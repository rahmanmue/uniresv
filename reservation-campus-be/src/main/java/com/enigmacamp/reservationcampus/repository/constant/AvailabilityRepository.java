package com.enigmacamp.reservationcampus.repository.constant;

import com.enigmacamp.reservationcampus.model.entity.constant.Availability;
import com.enigmacamp.reservationcampus.model.entity.constant.StatusReservation;
import com.enigmacamp.reservationcampus.utils.constant.EAvailability;
import com.enigmacamp.reservationcampus.utils.constant.EStatusReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, String> {
//    Optional<Availability> findByName(EAvailability name);

    @Query("SELECT a FROM Availability a WHERE a.name = :name")
    Availability findByName(@Param("name") EAvailability name);
}
