package com.enigmacamp.reservationcampus.services.constant;

import com.enigmacamp.reservationcampus.model.entity.constant.TypeFacilities;

import java.util.List;

public interface TypeFacilitiesService {

    void initTypeFacilities();
    List<TypeFacilities> getAll();
}
