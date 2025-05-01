package com.uit.librarymanagementapplication.service.TransactionLoanServices;

import com.uit.librarymanagementapplication.domain.DTO.GenreCategory.GenreCategoryDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanDetailDTO;
import com.uit.librarymanagementapplication.domain.DTO.TransactionLoan.TransactionLoanHeaderDTO;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanDetailRepositories.ITransactionLoanDetailRepository;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanDetailRepositories.TransactionLoanDetailRepository;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanHeaderRepositories.ITransactionLoanHeaderRepository;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanHeaderRepositories.TransactionLoanHeaderRepository;
import com.uit.librarymanagementapplication.service.GenreCategoryServices.GenreCategoryService;
import java.util.ArrayList;
import java.util.List;

public class TransactionLoanService implements ITransactionLoanService {

    private final ITransactionLoanHeaderRepository transactionLoanHeaderRepository;
    private final ITransactionLoanDetailRepository transactionLoanDetailRepository;
    private static TransactionLoanService instance;
    private static GenreCategoryService categoryService = new GenreCategoryService();

    public TransactionLoanService() {
        this.transactionLoanHeaderRepository = TransactionLoanHeaderRepository.getInstance();
        this.transactionLoanDetailRepository = TransactionLoanDetailRepository.getInstance();
    }

    public static TransactionLoanService getInstance() {
        if (instance == null) {
            instance = new TransactionLoanService();
        }
        return instance;
    }

    @Override
    public List<TransactionLoanHeaderDTO> getAllTransLoanHeaderByKeyword(String keyword, String column) {
        if (keyword == "") {
            return transactionLoanHeaderRepository.getAllTransHeader();
        } else {
            return transactionLoanHeaderRepository.getAllTransHeaderByKeyWord(keyword, column);
        }
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

}
