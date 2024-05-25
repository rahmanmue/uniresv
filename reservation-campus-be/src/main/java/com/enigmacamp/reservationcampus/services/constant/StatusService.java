package com.enigmacamp.reservationcampus.services.constant;

import com.enigmacamp.reservationcampus.model.entity.constant.StatusReservation;

import java.util.List;

public interface StatusService {
    void initStatus();
    List<StatusReservation> getAll();
}
