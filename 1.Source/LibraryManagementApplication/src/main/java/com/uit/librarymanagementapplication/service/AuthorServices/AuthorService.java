package com.uit.librarymanagementapplication.service.AuthorServices;

import com.uit.librarymanagementapplication.domain.DTO.Author.AuthorDTO;
import com.uit.librarymanagementapplication.domain.entity.Author;
import com.uit.librarymanagementapplication.domain.repository.AuthorRepositories.AuthorRepository;
import com.uit.librarymanagementapplication.domain.repository.AuthorRepositories.IAuthorRepository;
import com.uit.librarymanagementapplication.lib.Constants;
import com.uit.librarymanagementapplication.mapper.IAuthorMapper;
import java.util.ArrayList;
import java.util.List;


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
