package com.uit.librarymanagementapplication.service.TransactionLoanServices;

import com.uit.librarymanagementapplication.domain.DTO.Book.BookSendEmail;
import com.uit.librarymanagementapplication.domain.DTO.GenreCategory.GenreCategoryDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanDetailDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanDetailRequestDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderRequestDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderRevokeDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionSendEmail;
import com.uit.librarymanagementapplication.domain.DTO.User.UserRoleDTO;
import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.entity.Book;
import com.uit.librarymanagementapplication.domain.entity.TransactionLoanHeader;
import com.uit.librarymanagementapplication.domain.entity.User;
import com.uit.librarymanagementapplication.domain.repository.BookRepositories.BookRepository;
import com.uit.librarymanagementapplication.domain.repository.BookRepositories.IBookRepository;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanDetailRepositories.ITransactionLoanDetailRepository;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanDetailRepositories.TransactionLoanDetailRepository;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanHeaderRepositories.ITransactionLoanHeaderRepository;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanHeaderRepositories.TransactionLoanHeaderRepository;
import com.uit.librarymanagementapplication.domain.repository.UserRepositories.IUserRepository;
import com.uit.librarymanagementapplication.domain.repository.UserRepositories.UserRepository;
import com.uit.librarymanagementapplication.lib.ApiException;
import com.uit.librarymanagementapplication.lib.Constants;
import com.uit.librarymanagementapplication.lib.Constants.EmailConstants;
import com.uit.librarymanagementapplication.lib.Constants.TransLoanStatusConsts;
import com.uit.librarymanagementapplication.lib.EmailHelper;
import com.uit.librarymanagementapplication.service.GenreCategoryServices.GenreCategoryService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionLoanService implements ITransactionLoanService {

    private final ITransactionLoanHeaderRepository transactionLoanHeaderRepository;
    private final ITransactionLoanDetailRepository transactionLoanDetailRepository;
    private final IBookRepository bookRepository;
    private static TransactionLoanService instance;
    private static GenreCategoryService categoryService = new GenreCategoryService();
    private final IUserRepository userRepository;

    public TransactionLoanService() {
        this.transactionLoanHeaderRepository = TransactionLoanHeaderRepository.getInstance();
        this.transactionLoanDetailRepository = TransactionLoanDetailRepository.getInstance();
        this.bookRepository = BookRepository.getInstance();
        this.userRepository = UserRepository.getInstance();
    }

    public static TransactionLoanService getInstance() {
        if (instance == null) {
            instance = new TransactionLoanService();
        }
        return instance;
    }

    @Override
    public List<TransactionLoanHeaderDTO> getAllTransLoanHeaderByKeyword(String keyword, String column) {
        List<TransactionLoanHeaderDTO> transHeaders;
        if (keyword == null || keyword.trim().isEmpty()) {
            transHeaders = transactionLoanHeaderRepository.getAllTransHeader();
        } else {
            transHeaders = transactionLoanHeaderRepository.getAllTransHeaderByKeyWord(keyword, column);
        }

        for (TransactionLoanHeaderDTO trans : transHeaders) {
            String statusName;
            if (trans.getStatus() == TransLoanStatusConsts.BORROW) {
                statusName = "BORROW";
            } else if (trans.getStatus() == TransLoanStatusConsts.PAID) {
                statusName = "PAID";
            } else {
                statusName = "UNKNOWN";
            }
            trans.setStatusName(statusName);
        }

        return transHeaders;
    }

    @Override
    public List<TransactionLoanDetailDTO> getAllTransDetails(int LoadHeaderID) {
        List<GenreCategoryDTO> categories = categoryService.getAllGenreCategory();
        List<TransactionLoanDetailDTO> result = transactionLoanDetailRepository.gettTransactionLoanByHeaderID(LoadHeaderID);
        for (TransactionLoanDetailDTO detail : result) {
            String genreCategory = detail.getGenreCategory();
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
                detail.setGenreCategory(genreNamesJoined);
            }
        }

        return result;
    }

    @Override
    public void createTransactionLoan(TransactionLoanHeaderRequestDTO request) {
        try {
            DbUtils.beginTransaction();
            int headerId = transactionLoanHeaderRepository.createTransactionLoanHeader(request);
            transactionLoanDetailRepository.createTransactionLoanDetails(headerId, request.getLoanDetails());
            bookRepository.updateQtyAllocated(request.getLoanDetails());
            DbUtils.commit();

            TransactionSendEmail data = prepareDataSendEmail(headerId, request.getLoanDetails());
            String body = EmailHelper.generateLoanEmailContent(data);
            String subject = String.format(EmailConstants.LOAN_BOOK_SUBJECT, data.getLoanTicketNumber());
            EmailHelper.sendEmail(data.getEmail(), subject, body);
        } catch (Exception e) {
            DbUtils.rollback();
            throw new ApiException(Constants.ErrorTitle.TRANS, Constants.ErrorCode.CREATE_TRANS_FAILED, Constants.ErrorMessage.CREATE_TRANS_FAILED);
        } finally {
            DbUtils.close();

        }
    }

    @Override
    public void revokeTransactionLoan(TransactionLoanHeaderRevokeDTO request) {
        try {
            DbUtils.beginTransaction();
            transactionLoanHeaderRepository.updateStatusRevoke(request.getLoanHeaderID());
            bookRepository.decrementQtyAllocated(request.getLoanDetails());
            DbUtils.commit();
        } catch (Exception e) {
            DbUtils.rollback();
            throw new ApiException(Constants.ErrorTitle.TRANS, Constants.ErrorCode.REVOKE_TRANS_FAILED, Constants.ErrorMessage.REVOKE_TRANS_FAILED);
        } finally {
            DbUtils.close();
        }
    }

    private TransactionSendEmail prepareDataSendEmail(int tranHeader, List<TransactionLoanDetailRequestDTO> loanDetails) {
        TransactionLoanHeader transHeader = transactionLoanHeaderRepository.findTransHeaderLoan(tranHeader);
        List<BookSendEmail> bookDetails = new ArrayList<>();
        List<Book> allBooks = bookRepository.getAllBooks();
        List<UserRoleDTO> allUsers = userRepository.getAllUsersCustomer();
        TransactionSendEmail result = new TransactionSendEmail();
        result.setLoanTicketNumber(transHeader.getLoanTicketNumber());
        result.setTotalQty(transHeader.getTotalQty());
        result.setLoanDt(transHeader.getLoanDt());
        result.setLoanReturnDt(transHeader.getLoanReturnDt());
        for (UserRoleDTO user : allUsers) {
            if (user.getUserID() == transHeader.getUserID()) {
                result.setUseName(user.getUserName());
                result.setPhone(user.getPhone());
                result.setEmail(user.getEmail());
            }
        }
        Map<Integer, Book> bookMap = allBooks.stream()
                .collect(Collectors.toMap(Book::getBookID, book -> book));
        for (TransactionLoanDetailRequestDTO bookId : loanDetails) {
            Book book = bookMap.get(bookId.getLoadBookID());
            if (book != null) {
                BookSendEmail bookEmail = new BookSendEmail();
                bookEmail.setBookID(book.getBookID());
                bookEmail.setTitle(book.getTitle());
                bookEmail.setAuthor(book.getAuthor());
                bookDetails.add(bookEmail);
            }
        }
        result.setBookDetails(bookDetails);
        return result;
    }
}
