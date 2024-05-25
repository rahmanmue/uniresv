package com.enigmacamp.reservationcampus.services.constant.impl;

import com.enigmacamp.reservationcampus.model.entity.constant.StatusReservation;
import com.enigmacamp.reservationcampus.repository.constant.StatusRepository;
import com.enigmacamp.reservationcampus.services.constant.StatusService;
import com.enigmacamp.reservationcampus.utils.constant.EStatusReservation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @PostConstruct
    public void initStatus() {
        if(statusRepository.count() == 0){
            for(EStatusReservation statusReservation : EStatusReservation.values()){
                StatusReservation entity = new StatusReservation();
                entity.setStatus(statusReservation);
                statusRepository.save(entity);
            }
        }
    }

    @Override
    public List<StatusReservation> getAll() {
        return statusRepository.findAll();
    }
}
