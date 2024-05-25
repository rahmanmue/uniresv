package com.enigmacamp.reservationcampus.model.entity;


import com.enigmacamp.reservationcampus.model.entity.constant.Penalties;
import com.enigmacamp.reservationcampus.model.entity.constant.StatusReservation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "trx_reservation")
public class Transaction {

    @Id
    @Column(name = "id_reservation")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "document")
    private String document;

    @Column(name = "date_submission")
    private Date dateSubmission;

    @Column(name = "date_reservation")
    private Date dateReservation;

    @Column(name = "date_return")
    private Date dateReturn;



    @ManyToOne
    @JoinColumn(name = "id_profile")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private StatusReservation status;

    @ManyToOne
    @JoinColumn(name = "id_penalties")
    private Penalties penalties;

    @OneToMany(mappedBy = "transaction")
    @JsonIgnoreProperties("transaction")
    private List<TransactionDetail> transactionDetail;
}
