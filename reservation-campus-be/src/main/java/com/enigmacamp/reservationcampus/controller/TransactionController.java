package com.enigmacamp.reservationcampus.controller;

import com.enigmacamp.reservationcampus.model.entity.Transaction;
import com.enigmacamp.reservationcampus.model.request.TransactionRequest;
import com.enigmacamp.reservationcampus.model.request.UpdateStatusRequest;
import com.enigmacamp.reservationcampus.model.response.CommonResponse;
import com.enigmacamp.reservationcampus.model.response.PageResponseWrapper;
import com.enigmacamp.reservationcampus.model.response.TransactionDTO;
import com.enigmacamp.reservationcampus.services.TransactionDetailService;
import com.enigmacamp.reservationcampus.services.TransactionService;
import com.enigmacamp.reservationcampus.utils.constant.APIPath;
import com.enigmacamp.reservationcampus.utils.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIPath.API + APIPath.TRANSACTION)
public class TransactionController {

    TransactionDetailService transactionDetailService;
    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionDetailService transactionDetailService, TransactionService transactionService) {
        this.transactionDetailService = transactionDetailService;
        this.transactionService = transactionService;
    }


    @GetMapping(APIPath.DETAIL + "/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable("id") String id) {
        TransactionDTO result = transactionService.getTransactionById(id);
        CommonResponse<TransactionDTO> response = CommonResponse.<TransactionDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.MESSAGE_READ)
                .data(result)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }


    @GetMapping("/{name}")
    public ResponseEntity<?> getTransactionByProfileName(@PathVariable("name") String name,
                                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "size", defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionDTO> pageResult = transactionService.findTransactionsByProfileName(name, pageable);
        PageResponseWrapper<TransactionDTO> result = new PageResponseWrapper<>(pageResult);
        CommonResponse<PageResponseWrapper<TransactionDTO>> response = CommonResponse.<PageResponseWrapper<TransactionDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.MESSAGE_READ)
                .data(result)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping(APIPath.ALL)
    public ResponseEntity<?> getAllTransaction(@RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionDTO> pageResult = transactionService.getAllTransaction(pageable);
        PageResponseWrapper<TransactionDTO> result = new PageResponseWrapper<>(pageResult);
        CommonResponse<PageResponseWrapper<TransactionDTO>> response = CommonResponse.<PageResponseWrapper<TransactionDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(Message.MESSAGE_READ)
                .data(result)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping(APIPath.SUBMISSION)
    public ResponseEntity<?> saveTransaction(@RequestBody TransactionRequest transactionRequest) {
        String message = String.format(Message.MESSAGE_SAVE_SUBMISSION);
        System.out.println(transactionRequest);
        Transaction result = transactionService.saveTransaction(transactionRequest);

        CommonResponse<Transaction> response = CommonResponse.<Transaction>builder()
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .data(result)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTransaction(@RequestBody Transaction transaction) {
        String message = String.format(Message.MESSAGE_UPDATE);
        Transaction result = transactionService.updateTransaction(transaction);
        CommonResponse<Transaction> response = CommonResponse.<Transaction>builder()
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .data(result)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping(APIPath.DELETE + "/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("id") String id) {
        String message = String.format(Message.MESSAGE_DELETE);
        transactionService.deleteTransaction(id);
        CommonResponse<TransactionDTO> response = CommonResponse.<TransactionDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping(APIPath.CANCEL + "/{id}")
    public ResponseEntity<?> cancelTransaction(@PathVariable("id") String id) {
        String message = String.format(Message.MESSAGE_CANCELED);
        transactionService.cancelTransaction(id);
        CommonResponse<TransactionDTO> response = CommonResponse.<TransactionDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping(APIPath.STATUS_RESERVATION + "/{id}")
    public ResponseEntity<?> changeStatusTransaction(@PathVariable String id, @RequestBody UpdateStatusRequest updateStatusRequest) {
        String message = String.format(Message.MESSAGE_UPDATE);
        transactionService.updateTransactionStatus(id, updateStatusRequest);
        CommonResponse<TransactionDTO> response = CommonResponse.<TransactionDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping(APIPath.USER + "/{userId}")
    public ResponseEntity<PageResponseWrapper<TransactionDTO>> getTransactionsByUserId(
            @PathVariable("userId") String userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionDTO> transactionPage = transactionService.findTransactionsByUserId(userId, pageable);

        PageResponseWrapper<TransactionDTO> response = new PageResponseWrapper<>(transactionPage);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
