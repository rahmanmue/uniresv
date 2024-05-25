package com.enigmacamp.reservationcampus.repository;

import com.enigmacamp.reservationcampus.model.entity.Facility;
import com.enigmacamp.reservationcampus.model.response.FacilityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, String> {

    @Query("SELECT f FROM Facility f WHERE f.id NOT IN (" +
            "SELECT td.facility.id FROM TransactionDetail td " +
            "JOIN td.transaction t " +
            "WHERE t.dateReservation <= :endDate AND t.dateReturn >= :startDate " +
            "AND (t.status IS NULL OR t.status.status IN ('STATUS_REJECTED', 'STATUS_CANCELED')))")
    Page<Facility> findAvailableFacilities(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT f FROM Facility f WHERE f.id IN (" +
            "SELECT td.facility.id FROM TransactionDetail td " +
            "JOIN td.transaction t " +
            "WHERE t.dateReservation <= :endDate AND t.dateReturn >= :startDate " +
            "AND (t.status IS NOT NULL AND t.status.status NOT IN ('STATUS_REJECTED', 'STATUS_CANCELED')))")
    Page<Facility> findUnavailableFacilities(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT f FROM Facility f WHERE f.name LIKE %:name% AND f.id NOT IN (" +
            "SELECT td.facility.id FROM TransactionDetail td " +
            "JOIN td.transaction t " +
            "WHERE t.dateReservation <= :endDate AND t.dateReturn >= :startDate " +
            "AND (t.status IS NULL OR t.status.status IN ('STATUS_REJECTED', 'STATUS_CANCELED')))")
    Page<Facility> findAvailableFacilitiesByName(String name, Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT f FROM Facility f WHERE f.name LIKE %:name% AND f.id IN (" +
            "SELECT td.facility.id FROM TransactionDetail td " +
            "JOIN td.transaction t " +
            "WHERE t.dateReservation <= :endDate AND t.dateReturn >= :startDate " +
            "AND (t.status IS NOT NULL AND t.status.status NOT IN ('STATUS_REJECTED', 'STATUS_CANCELED')))")
    Page<Facility> findUnavailableFacilitiesByName(String name, Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT f FROM Facility f WHERE f.typeFacilities.id = :typeId AND f.id NOT IN (" +
            "SELECT td.facility.id FROM TransactionDetail td " +
            "JOIN td.transaction t " +
            "WHERE t.dateReservation <= :endDate AND t.dateReturn >= :startDate " +
            "AND (t.status IS NULL OR t.status.status IN ('STATUS_REJECTED', 'STATUS_CANCELED')))")
    Page<Facility> findAvailableFacilitiesByType(String typeId, Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT f FROM Facility f WHERE f.typeFacilities.id = :typeId AND f.id IN (" +
            "SELECT td.facility.id FROM TransactionDetail td " +
            "JOIN td.transaction t " +
            "WHERE t.dateReservation <= :endDate AND t.dateReturn >= :startDate " +
            "AND (t.status IS NOT NULL AND t.status.status NOT IN ('STATUS_REJECTED', 'STATUS_CANCELED')))")
    Page<Facility> findUnavailableFacilitiesByType(String typeId, Date startDate, Date endDate, Pageable pageable);

    FacilityResponse getFacilityById(String id);
}
