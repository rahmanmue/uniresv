package com.enigmacamp.reservationcampus.repository;

import com.enigmacamp.reservationcampus.model.entity.constant.Penalties;
import com.enigmacamp.reservationcampus.utils.constant.EPenalties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PenaltiesRepository extends JpaRepository<Penalties, String> {

    @Query("SELECT p FROM Penalties p WHERE p.name = :penaltyName")
    Penalties findByName(@Param("penaltyName") EPenalties penalties);


}
