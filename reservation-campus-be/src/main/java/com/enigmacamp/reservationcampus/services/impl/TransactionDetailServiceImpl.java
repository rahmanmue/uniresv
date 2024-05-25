package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.model.entity.TransactionDetail;
import com.enigmacamp.reservationcampus.repository.TransactionDetailRepository;
import com.enigmacamp.reservationcampus.services.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

    TransactionDetailRepository transactionDetailRepository;

    @Autowired
    public TransactionDetailServiceImpl(TransactionDetailRepository transactionDetailRepository) {
        this.transactionDetailRepository = transactionDetailRepository;
    }

    @Override
    public TransactionDetail saveTransactionDetail(TransactionDetail transactionDetail) {
        return transactionDetailRepository.save(transactionDetail);
    }

    @Override
    public TransactionDetail getTransactionDetailById(String id) {
        return null;
    }

    @Override
    public TransactionDetail updateTransactionDetail(TransactionDetail transactionDetail) {
        return null;
    }

    @Override
    public void deleteTransactionDetail(String id) {

    }
}
