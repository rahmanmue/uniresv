package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.model.entity.TransactionDetail;
import com.enigmacamp.reservationcampus.repository.TransactionDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TransactionDetailServiceImplTest {

    @Mock
    private TransactionDetailRepository transactionDetailRepository;

    @InjectMocks
    private TransactionDetailServiceImpl transactionDetailService;

    private TransactionDetail transactionDetail;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionDetail = new TransactionDetail();
        transactionDetail.setId("1");
    }

    @Test
    void saveTransactionDetail_success() {
        when(transactionDetailRepository.save(any(TransactionDetail.class))).thenReturn(transactionDetail);

        TransactionDetail savedTransactionDetail = transactionDetailService.saveTransactionDetail(transactionDetail);

        assertNotNull(savedTransactionDetail);
        verify(transactionDetailRepository, times(1)).save(any(TransactionDetail.class));
    }
}
