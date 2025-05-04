package com.uit.librarymanagementapplication.service.TransactionLoanServices;

import com.uit.librarymanagementapplication.domain.DTO.GenreCategory.GenreCategoryDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanDetailDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderRequestDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderRevokeDTO;
import com.uit.librarymanagementapplication.domain.DbUtils;
import com.uit.librarymanagementapplication.domain.repository.BookRepositories.BookRepository;
import com.uit.librarymanagementapplication.domain.repository.BookRepositories.IBookRepository;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanDetailRepositories.ITransactionLoanDetailRepository;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanDetailRepositories.TransactionLoanDetailRepository;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanHeaderRepositories.ITransactionLoanHeaderRepository;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanHeaderRepositories.TransactionLoanHeaderRepository;
import com.uit.librarymanagementapplication.lib.Constants.TransLoanStatusConsts;
import com.uit.librarymanagementapplication.service.GenreCategoryServices.GenreCategoryService;
import java.util.ArrayList;
import java.util.List;

public class TransactionLoanService implements ITransactionLoanService {

    private final ITransactionLoanHeaderRepository transactionLoanHeaderRepository;
    private final ITransactionLoanDetailRepository transactionLoanDetailRepository;
    //private final IBookRepository bookRepository;
    private static TransactionLoanService instance;
    private static GenreCategoryService categoryService = new GenreCategoryService();

    public TransactionLoanService() {
        this.transactionLoanHeaderRepository = TransactionLoanHeaderRepository.getInstance();
        this.transactionLoanDetailRepository = TransactionLoanDetailRepository.getInstance();
        //this.bookRepository = BookRepository.getInstance();
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
            //bookRepository.updateQtyAllocated(request.getLoanDetails());
            DbUtils.commit();
        } catch (Exception e) {
            DbUtils.rollback();
            System.err.println("Failed to create transaction loan: " + e.getMessage());
        } finally {
            DbUtils.close();

        }
    }

    @Override
    public void revokeTransactionLoan(TransactionLoanHeaderRevokeDTO request) {
        try {
            DbUtils.beginTransaction();
            transactionLoanHeaderRepository.updateStatusRevoke(request.getLoanHeaderID());
            //bookRepository.decrementQtyAllocated(request.getLoanDetails());
            DbUtils.commit();
        } catch (Exception e) {
            DbUtils.rollback();
            System.err.println("Failed to revoke transaction loan: " + e.getMessage());
        } finally {
            DbUtils.close();
        }
    }

}
