package com.enigmacamp.reservationcampus.services.constant.impl;

import com.enigmacamp.reservationcampus.model.entity.constant.Penalties;
import com.enigmacamp.reservationcampus.repository.constant.PenaltyRepository;
import com.enigmacamp.reservationcampus.services.constant.PenaltyService;
import com.enigmacamp.reservationcampus.utils.constant.EPenalties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PenaltyServiceImpl implements PenaltyService {

    @Autowired
    private PenaltyRepository penaltyRepository;

    @PostConstruct
    public void initPenalty() {
        if(penaltyRepository.count() == 0){
            for(EPenalties ePenalties : EPenalties.values()){
                Penalties entity = new Penalties();
                entity.setName(ePenalties);
                penaltyRepository.save(entity);
            }
        }

    }

    @Override
    public List<Penalties> getAllPenalties() {
        return penaltyRepository.findAll();
    }
}
