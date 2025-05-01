package com.uit.librarymanagementapplication.service.GenreCategoryServices;

import com.uit.librarymanagementapplication.domain.DTO.GenreCategory.GenreCategoryDTO;
import com.uit.librarymanagementapplication.domain.entity.GenreCategory;
import com.uit.librarymanagementapplication.domain.repository.GenreCategoryRepositories.GenreCategoryRepository;
import com.uit.librarymanagementapplication.domain.repository.GenreCategoryRepositories.IGenreCategoryRepository;
import com.uit.librarymanagementapplication.mapper.IGenreCategoryMapper;
import java.util.List;


public class GenreCategoryService implements IGenreCategoryService {

    private final IGenreCategoryRepository genreCategoryRepository;

    private static GenreCategoryService instance;

    public GenreCategoryService() {
        this.genreCategoryRepository = GenreCategoryRepository.getInstance();
    }

    public static GenreCategoryService getInstance() {
        if (instance == null) {
            instance = new GenreCategoryService();
        }
        return instance;
    }

    @Override
    public List<GenreCategoryDTO> getAllGenreCategory() {
        List<GenreCategory> result = genreCategoryRepository.getAllCategories();
        return IGenreCategoryMapper.INSTANCE.toDTOList(result);
    }

}
