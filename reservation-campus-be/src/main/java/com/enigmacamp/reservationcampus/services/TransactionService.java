package com.enigmacamp.reservationcampus.services;

import com.enigmacamp.reservationcampus.model.entity.Transaction;
import com.enigmacamp.reservationcampus.model.request.TransactionRequest;
import com.enigmacamp.reservationcampus.model.request.UpdateStatusRequest;
import com.enigmacamp.reservationcampus.model.response.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    Transaction saveTransaction(TransactionRequest transaction);

    TransactionDTO getTransactionById(String id);

    Page<TransactionDTO> getAllTransaction(Pageable pageable);

//    Page<TransactionDTO> findTransactionsbySubject(String name, Pageable pageable);

    Page<TransactionDTO> findTransactionsByProfileName(String name, Pageable pageable);

    Transaction updateTransaction(Transaction transaction);

    void deleteTransaction(String id);

    void cancelTransaction(String id);

    void updateTransactionStatus(String id, UpdateStatusRequest request);

    Page<TransactionDTO> findTransactionsByUserId(String userId, Pageable pageable);
}
