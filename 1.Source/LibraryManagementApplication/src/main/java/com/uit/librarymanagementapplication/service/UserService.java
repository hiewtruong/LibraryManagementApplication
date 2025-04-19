package com.uit.librarymanagementapplication.service;

import com.uit.librarymanagementapplication.domain.DTO.UserDTO;
import com.uit.librarymanagementapplication.domain.UtilService;
import com.uit.librarymanagementapplication.domain.model.User;
import com.uit.librarymanagementapplication.domain.repository.UserRepository;
import com.uit.librarymanagementapplication.lib.ApiException;
import com.uit.librarymanagementapplication.lib.Constants.*;
import com.uit.librarymanagementapplication.lib.Constants.GeneralStatus;
import com.uit.librarymanagementapplication.mapper.UserMapper;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    private static UserService instance;

    public UserService() {
        
    }

    public UserDTO login(String username, String password, boolean isAdmin) {
        UserDTO userDTO = null;
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ApiException(ErrorTitle.LOGIN, ErrorCode.USER_NOT_FOUND, ErrorMessage.USER_NOT_FOUND);
        }
        userDTO = UserMapper.INSTANCE.toDTO(user);
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
