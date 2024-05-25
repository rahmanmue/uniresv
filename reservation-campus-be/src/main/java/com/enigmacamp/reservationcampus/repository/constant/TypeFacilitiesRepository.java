package com.enigmacamp.reservationcampus.repository.constant;

import com.enigmacamp.reservationcampus.model.entity.constant.TypeFacilities;
import com.enigmacamp.reservationcampus.utils.constant.ETypeFacilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeFacilitiesRepository extends JpaRepository<TypeFacilities, String> {
    Optional <TypeFacilities> findByName(ETypeFacilities name);

}
