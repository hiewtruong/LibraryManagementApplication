package com.uit.librarymanagementapplication.service.UserServices;

import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.domain.UtilService;
import com.uit.librarymanagementapplication.domain.entity.User;
import com.uit.librarymanagementapplication.domain.repository.UserRepositories.IUserRepository;
import com.uit.librarymanagementapplication.domain.repository.UserRepositories.UserRepository;
import com.uit.librarymanagementapplication.lib.ApiException;
import com.uit.librarymanagementapplication.lib.Constants;
import com.uit.librarymanagementapplication.lib.Constants.EmailConstants;
import com.uit.librarymanagementapplication.lib.Constants.ErrorCode;
import com.uit.librarymanagementapplication.lib.Constants.ErrorMessage;
import com.uit.librarymanagementapplication.lib.Constants.ErrorTitle;
import com.uit.librarymanagementapplication.lib.Constants.GeneralStatus;
import com.uit.librarymanagementapplication.lib.EmailHelper;
import com.uit.librarymanagementapplication.mapper.IUserMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IUserMapper userMapper = IUserMapper.INSTANCE;
    private static UserService instance;

    public UserService() {
        this.userRepository = UserRepository.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    @Override
    public UserDTO login(String username, String password, boolean isAdmin) {
        UserDTO userDTO = null;
        UserRoleDTO user = userRepository.findByUsername(username);

        if (user == null) {
            throw new ApiException(ErrorTitle.LOGIN, ErrorCode.USER_NOT_FOUND, ErrorMessage.USER_NOT_FOUND);
        }
        userDTO = IUserMapper.INSTANCE.toDTO(user);

        if (isAdmin && userDTO.getIsAdmin() == 0) {
            throw new ApiException(ErrorTitle.LOGIN, ErrorCode.USER_IS_NOT_ADMIN, ErrorMessage.USER_IS_NOT_ADMIN);
        }
        var isValid = ComparePassword(password, userDTO.getPassword());
        if (!isValid) {
            throw new ApiException(ErrorTitle.LOGIN, ErrorCode.PASSWORD_NOT_CORRECT, ErrorMessage.PASSWORD_NOT_CORRECT);
        }
        if (userDTO.getIsDelete() == GeneralStatus.DELETE) {
            throw new ApiException(ErrorTitle.LOGIN, ErrorCode.USER_HAS_BEEN_LOCKED, ErrorMessage.USER_HAS_BEEN_LOCKED);
        }
        return userDTO;
    }

    private boolean ComparePassword(String password, String passwordHash) {
        boolean isValid = false;
        var encryptPassword = UtilService.encrypt(password);
        if (passwordHash.equals(encryptPassword)) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public UserDTO loginUser(String username, String password) {
        UserDTO user = userRepository.findUserByUsernameAndPassword(username);
        if (user == null) {
            throw new ApiException(ErrorTitle.LOGIN, ErrorCode.USER_NOT_FOUND, ErrorMessage.USER_NOT_FOUND);
        }
        boolean isValid = ComparePassword(password, user.getPassword());
        if (!isValid) {
            throw new ApiException(ErrorTitle.LOGIN, ErrorCode.PASSWORD_NOT_CORRECT, ErrorMessage.PASSWORD_NOT_CORRECT);
        }
        if (user.getIsDelete() == GeneralStatus.DELETE) {
            throw new ApiException(ErrorTitle.LOGIN, ErrorCode.USER_HAS_BEEN_LOCKED, ErrorMessage.USER_HAS_BEEN_LOCKED);
        }
        return user;
    }

    @Override
    public List<UserRoleDTO> getAllUsers() {
        return userRepository.getAllUsersCustomer();
    }

    @Override
    public List<UserRoleDTO> getAllUsersByKeyword(String keyword, String column) {
        List<UserRoleDTO> users;
        if (keyword == null || keyword.trim().isEmpty()) {
            users = userRepository.getAllUsersCustomer();
        } else {
            users = userRepository.getAllUsersCustomerBySearch(keyword, column);
        }
        for (UserRoleDTO user : users) {
            String encryptedPassword = user.getPassword();
            if (encryptedPassword != null) {
                String decryptedPassword = UtilService.decrypt(encryptedPassword);
                user.setPassword(decryptedPassword);
            }
        }
        return users;
    }

    @Override
    public void createUser(UserRoleDTO user) {
        try {
            String password = UtilService.encrypt(user.getPassword());
            User newUser = IUserMapper.INSTANCE.toEntity(user);
            newUser.setUserRoleID(Constants.USER_ROLE);
            newUser.setIsDelete(GeneralStatus.OPEN);
            newUser.setCreatedBy(Constants.ADMIN);
            newUser.setUpdateBy(Constants.ADMIN);
            newUser.setPassword(password);
            boolean create = userRepository.createUser(newUser);
            if (!create) {
                throw new ApiException(Constants.ErrorTitle.USER, Constants.ErrorCode.CREATE_USER_FAILD, Constants.ErrorMessage.CREATE_USER_FAILD);
            }
            String body = EmailHelper.generateAccountEmailContent(user.getUserName(), user.getPassword());
            EmailHelper.sendEmail(user.getEmail(), EmailConstants.EMAIL_SUBJECT_ACCOUNT_CREATED, body);
        } catch (ApiException e) {
            throw new ApiException(Constants.ErrorTitle.USER, Constants.ErrorCode.CREATE_USER_FAILD, Constants.ErrorMessage.CREATE_USER_FAILD);
        }
    }

    @Override
    public void updateUser(UserRoleDTO userRequest) {

        try {
            User existingUser = userRepository.getUser(userRequest.getUserID());
            if (existingUser == null) {
                throw new ApiException(Constants.ErrorTitle.USER, Constants.ErrorCode.NOT_FOUND_USER, Constants.ErrorMessage.USER_NOT_FOUND);
            }
            User updatedUser = IUserMapper.INSTANCE.toEntity(userRequest);
            String password = UtilService.encrypt(userRequest.getPassword());
            userRequest.setPassword(password);
            updatedUser.setUserID(existingUser.getUserID());
            updatedUser.setUpdateDt(new Date());
            updatedUser.setUpdateBy(Constants.ADMIN);

            boolean updated = userRepository.updateUser(updatedUser);
            if (!updated) {
                throw new ApiException(Constants.ErrorTitle.USER, Constants.ErrorCode.UPDATE_USER_FAILD, Constants.ErrorMessage.UPDATE_USER_FAILD);
            }
        } catch (ApiException e) {
        }
    }

    @Override
    public void deleteUser(int userID) {
        boolean isSuccess = userRepository.deleteUser(userID);
        if (!isSuccess) {
            throw new ApiException(Constants.ErrorTitle.USER, Constants.ErrorCode.DELETE_USER, Constants.ErrorMessage.DELETE_USER_FAILD);
        }
    }

    @Override
    public boolean checkDuplicateEmail(String email) {
        return userRepository.checkUniqEmail(email);

    }

    @Override
    public boolean checkDuplicateUserName(String userName) {
       return userRepository.checkUsernameUniq(userName);
    }
}
