package com.enigmacamp.reservationcampus.repository;

import com.enigmacamp.reservationcampus.model.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<Facility, String> {

    @Query("SELECT f.typeFacilities.name, COUNT(f) FROM Facility f GROUP BY f.typeFacilities.name")
    List<Object[]> countFacilitiesByType();

//    @Query("SELECT f.availability.name, COUNT(f) " +
//            "FROM Facility f LEFT JOIN TransactionDetail td ON f.id = td.facility.id " +
//            "WHERE td.transaction.dateReservation <= CURRENT_DATE AND td.transaction.dateReturn >= CURRENT_DATE " +
//            "GROUP BY f.availability.name")
//    List<Object[]> countFacilitiesByAvailability();

//    @Query("SELECT f.availability.name, COUNT(td) " +
//            "FROM Facility f LEFT JOIN TransactionDetail td ON f.id = td.facility.id " +
//            "AND td.transaction.dateReservation <= CURRENT_DATE AND td.transaction.dateReturn >= CURRENT_DATE " +
//            "GROUP BY f.availability.name")
//    List<Object[]> countFacilitiesByAvailability();

    @Query("SELECT f.availability.name, COUNT(f) " +
            "FROM Facility f " +
            "WHERE f.id NOT IN (SELECT td.facility.id FROM TransactionDetail td " +
            "JOIN td.transaction t " +
            "WHERE t.dateReservation <= CURRENT_DATE AND t.dateReturn >= CURRENT_DATE " +
            "AND (t.status IS NULL OR t.status.status IN ('STATUS_REJECTED', 'STATUS_CANCELED'))) " +
            "GROUP BY f.availability.name")
    List<Object[]> countFacilitiesByAvailability();



    @Query("SELECT t.status.status, COUNT(t) FROM Transaction t GROUP BY t.status.status")
    List<Object[]> countTransactionsByStatus();
}
