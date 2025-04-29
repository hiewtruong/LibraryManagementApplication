/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uit.librarymanagementapplication.service.AuthorServices;

import com.uit.librarymanagementapplication.domain.DTO.Author.AuthorDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.domain.entity.Author;
import com.uit.librarymanagementapplication.domain.repository.AuthorRepositories.AuthorRepository;
import com.uit.librarymanagementapplication.domain.repository.AuthorRepositories.IAuthorRepository;
import com.uit.librarymanagementapplication.domain.repository.UserRepositories.IUserRepository;
import com.uit.librarymanagementapplication.domain.repository.UserRepositories.UserRepository;
import com.uit.librarymanagementapplication.lib.Constants;
import com.uit.librarymanagementapplication.mapper.IAuthorMapper;
import com.uit.librarymanagementapplication.service.UserServices.IUserService;
import com.uit.librarymanagementapplication.service.UserServices.UserService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public class AuthorService implements IAuthorService {

    private final IAuthorRepository authorRepository;

    private static AuthorService instance;

    public AuthorService() {
        this.authorRepository = AuthorRepository.getInstance();
    }

    public static AuthorService getInstance() {
        if (instance == null) {
            instance = new AuthorService();
        }
        return instance;
    }

    @Override
    public List<AuthorDTO> getAuthorByName(String keyword) {
        List<AuthorDTO> result = new ArrayList<AuthorDTO>();
        List<Author> dbResult = new ArrayList<Author>();
        if (keyword == "") {
            dbResult = authorRepository.getAllAuthors();
        } else {
            dbResult = authorRepository.getAuthorsByName(keyword);
        }
        return IAuthorMapper.INSTANCE.toDTOList(dbResult);
    }

    @Override
    public boolean createAuthors(String authorName) {
        boolean isSuccess = false;
        Author request = new Author(authorName,Constants.ADMIN,Constants.ADMIN);
        isSuccess = authorRepository.createAuthor(request);
        return isSuccess;
    }
}
