package com.uit.librarymanagementapplication.service.BookServices;

import com.uit.librarymanagementapplication.domain.DTO.Book.BookDTO;
import com.uit.librarymanagementapplication.domain.DTO.GenreCategory.GenreCategoryDTO;
import com.uit.librarymanagementapplication.domain.entity.Book;
import com.uit.librarymanagementapplication.domain.repository.BookRepositories.BookRepository;
import com.uit.librarymanagementapplication.domain.repository.BookRepositories.IBookRepository;
import com.uit.librarymanagementapplication.domain.repository.GenreCategoryRepositories.IGenreCategoryRepository;
import com.uit.librarymanagementapplication.mapper.IBookMapper;
import com.uit.librarymanagementapplication.service.GenreCategoryServices.GenreCategoryService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hieutruong
 */
public class BookService implements IBookService {

    private final IBookRepository bookRepository;
    private static GenreCategoryService categoryService = new GenreCategoryService();


    private static BookService instance;

    public BookService() {
        this.bookRepository = BookRepository.getInstance();
    }

    public static BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    @Override
    public List<BookDTO> getAllBook() {
        List<Book> books = bookRepository.getAllBooks();
        List<BookDTO> result = IBookMapper.INSTANCE.toDTOList(books);
        List<GenreCategoryDTO> categories = categoryService.getAllGenreCategory();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            BookDTO dto = result.get(i);
            boolean isOutStock = (book.getQtyOH() - book.getQtyAllocated()) == 0;
            dto.setIsOutOfStock(isOutStock);
            
            String genreCategory = dto.getGenreCategory();
            if (genreCategory != null && !genreCategory.isEmpty()) {
                String[] genreIds = genreCategory.split(",");
                List<String> genreNames = new ArrayList<>();
                for (String genreId : genreIds) {
                    try {
                        int id = Integer.parseInt(genreId.trim());
                        for (GenreCategoryDTO category : categories) {
                            if (category.getGenreCategoryID() == id) {
                                genreNames.add(category.getNameCategory());
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
                String genreNamesJoined = String.join(",", genreNames);
                dto.setGenreCategory(genreNamesJoined);
            }
        }
        return result;
    }

}
