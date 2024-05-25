package com.enigmacamp.reservationcampus.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "trx_reservation_detail")
public class TransactionDetail {

    @Id
    @Column(name = "id_reserv_detail")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Integer price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "id_fac")
    private Facility facility;

    @ManyToOne
    @JoinColumn(name = "id_reservation")
    @JsonIgnoreProperties("transactionDetail")
    private Transaction transaction;
}
