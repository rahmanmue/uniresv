package com.enigmacamp.reservationcampus.services.constant;

import com.enigmacamp.reservationcampus.model.entity.constant.Penalties;

import java.util.List;

public interface PenaltyService {
    void initPenalty();
    List<Penalties> getAllPenalties();
}
