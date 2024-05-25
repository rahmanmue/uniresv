package com.enigmacamp.reservationcampus.services;

import com.enigmacamp.reservationcampus.model.entity.TransactionDetail;

public interface TransactionDetailService {
    TransactionDetail saveTransactionDetail(TransactionDetail transactionDetail);

    TransactionDetail getTransactionDetailById(String id);

    TransactionDetail updateTransactionDetail(TransactionDetail transactionDetail);

    void deleteTransactionDetail(String id);

}
