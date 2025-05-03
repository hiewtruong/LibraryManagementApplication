package com.uit.librarymanagementapplication.controller;

import com.uit.librarymanagementapplication.domain.DTO.Book.BookDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserDTO;
import com.uit.librarymanagementapplication.domain.DTO.User.UserWishListDTO;
import com.uit.librarymanagementapplication.service.BookServices.BookService;
import com.uit.librarymanagementapplication.service.UserServices.IUserWishListService;
import com.uit.librarymanagementapplication.service.UserServices.UserWishListService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BookController {

    private final BookService bookService = new BookService();
    private final IUserWishListService wishListService = new UserWishListService();
    private UserDTO currentUser;

    public List<BookDTO> findBooksByName(String name) {
        return bookService.getAllBook().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(int id) {
        return bookService.getAllBook().stream()
                .filter(book -> book.getBookID() == id)
                .findFirst()
                .orElse(null);
    }

    public List<BookDTO> getAllBooks() {
        return bookService.getAllBook();
    }

    public void setCurrentUser(UserDTO user) {
        this.currentUser = user;
    }

    public String handleRentBook(int userId, BookDTO book) {
      
        if (book.getQtyOH() <= 0) {
            return "Sách đã hết hàng!";
        }

        boolean isUpdated = bookService.updateBookQuantity(book);
        if (!isUpdated) {
            return "Không thể cập nhật số lượng sách!";
        }

        if (currentUser == null) {
            return "Người dùng chưa đăng nhập!";
        }

        UserWishListDTO dto = new UserWishListDTO(
           userId,
            book.getBookID(),
            0,
            new Date(),
            currentUser.getUserName(),
            new Date(),
            currentUser.getUserName()
        );

        try {
            wishListService.addToWishList(dto);
            return "Bạn đã thuê sách thành công!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Thuê không thành công";
        }
    }
}