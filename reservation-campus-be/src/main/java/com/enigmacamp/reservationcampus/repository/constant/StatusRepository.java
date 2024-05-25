package com.enigmacamp.reservationcampus.repository.constant;

import com.enigmacamp.reservationcampus.model.entity.constant.StatusReservation;
import com.enigmacamp.reservationcampus.utils.constant.EStatusReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusReservation, String> {
    @Query("SELECT s FROM StatusReservation s WHERE s.status = :status")
    StatusReservation findByStatus(@Param("status") EStatusReservation status);


}
