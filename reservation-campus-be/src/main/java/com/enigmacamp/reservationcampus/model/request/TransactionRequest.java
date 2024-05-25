package com.enigmacamp.reservationcampus.model.request;

import com.enigmacamp.reservationcampus.model.entity.TransactionDetail;
import com.enigmacamp.reservationcampus.model.response.TransactionDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {
    private String id_profile;
    private String subject;
    private String document;
    private Date dateReservation;
    private Date dateReturn;
    private Integer totalItem;
    private Integer grandTotal;
    List<TransactionDetailDTO> transactionDetail;
}
