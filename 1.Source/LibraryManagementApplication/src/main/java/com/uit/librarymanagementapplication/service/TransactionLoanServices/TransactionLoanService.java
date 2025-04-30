package com.uit.librarymanagementapplication.service.TransactionLoanServices;

import com.uit.librarymanagementapplication.domain.DTO.TransactionLoanHeader.TransactionLoanHeaderDTO;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanHeaderRepositories.ITransactionLoanHeaderRepository;
import com.uit.librarymanagementapplication.domain.repository.TransactionLoanHeaderRepositories.TransactionLoanHeaderRepository;
import java.util.List;


public class TransactionLoanService implements ITransactionLoanService {

  private final ITransactionLoanHeaderRepository transactionLoanHeaderRepository;

    private static TransactionLoanService instance;

    public TransactionLoanService() {
        this.transactionLoanHeaderRepository = TransactionLoanHeaderRepository.getInstance();
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
           return transactionLoanHeaderRepository.getAllTransHeaderByKeyWord(keyword,column);
        }
    }

}
