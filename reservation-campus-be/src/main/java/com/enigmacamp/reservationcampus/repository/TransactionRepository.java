package com.enigmacamp.reservationcampus.repository;

import com.enigmacamp.reservationcampus.model.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query("SELECT t FROM Transaction t JOIN t.transactionDetail d WHERE d.facility.id = :facilityId AND " +
            "((:startDate BETWEEN t.dateReservation AND t.dateReturn) OR " +
            "(:endDate BETWEEN t.dateReservation AND t.dateReturn) OR " +
            "(t.dateReservation BETWEEN :startDate AND :endDate))")
    List<Transaction> findReservationsForFacilityWithinDates(@Param("facilityId") String facilityId,
                                                             @Param("startDate") Date startDate,
                                                             @Param("endDate") Date endDate);

//    Page<Transaction> findBySubject(String subject, Pageable pageable);
    Page<Transaction> findAll(Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE LOWER(t.profile.fullName) LIKE LOWER(concat('%', :name, '%'))")
    Page<Transaction> findByProfileFullNameIgnoreCase(@Param("name") String name, Pageable pageable);

    @Query("SELECT t FROM Transaction t JOIN t.profile p JOIN p.user u WHERE u.id = :userId")
    Page<Transaction> findByUserId(@Param("userId") String userId, Pageable pageable);
}

