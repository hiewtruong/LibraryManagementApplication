package com.uit.librarymanagementapplication.service;

import com.uit.librarymanagementapplication.domain.DTO.UserDTO;
import com.uit.librarymanagementapplication.domain.DTO.UserRoleDTO;
import com.uit.librarymanagementapplication.domain.UtilService;
import com.uit.librarymanagementapplication.domain.model.User;
import com.uit.librarymanagementapplication.domain.repository.IUserRepository;
import com.uit.librarymanagementapplication.domain.repository.UserRepository;
import com.uit.librarymanagementapplication.lib.ApiException;
import com.uit.librarymanagementapplication.lib.Constants.*;
import com.uit.librarymanagementapplication.lib.Constants.GeneralStatus;
import com.uit.librarymanagementapplication.mapper.UserMapper;

public class UserService implements IUserService  {

    private final IUserRepository userRepository;

    private static UserService instance;

    public UserService() {
        this.userRepository = UserRepository.getInstance(); // sử dụng interface thay vì new trực tiếp
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
        userDTO = UserMapper.INSTANCE.toDTO(user);
           System.out.println("user"+userDTO.getIsAdmin());
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
}
