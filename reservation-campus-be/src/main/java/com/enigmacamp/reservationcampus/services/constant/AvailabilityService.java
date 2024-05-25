package com.enigmacamp.reservationcampus.services.constant;

import com.enigmacamp.reservationcampus.model.entity.constant.Availability;

import java.util.List;

public interface AvailabilityService {

    void initAvailability();
    List<Availability> getAll();

}
