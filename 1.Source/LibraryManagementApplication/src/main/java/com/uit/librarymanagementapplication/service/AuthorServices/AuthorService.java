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
    public List<AuthorDTO> getAuthorByName(String authorName) {
        List<AuthorDTO> result = new  ArrayList<AuthorDTO>();
        if (authorName == "") {
            List<Author> dbResult = authorRepository.getAllAuthors();
            result = IAuthorMapper.INSTANCE.toDTOList(dbResult);
        } else {

        }
        return result;
    }
}
