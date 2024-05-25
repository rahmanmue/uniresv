package com.enigmacamp.reservationcampus.services.constant.impl;

import com.enigmacamp.reservationcampus.model.entity.constant.Availability;
import com.enigmacamp.reservationcampus.repository.constant.AvailabilityRepository;
import com.enigmacamp.reservationcampus.services.constant.AvailabilityService;
import com.enigmacamp.reservationcampus.utils.constant.EAvailability;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @PostConstruct
    public void initAvailability() {
        if(availabilityRepository.count() == 0) {
            for(EAvailability availability : EAvailability.values()) {
                Availability entity = new Availability();
                entity.setName(availability);
                availabilityRepository.save(entity);
            }
        }
    }

    @Override
    public List<Availability> getAll() {
        return availabilityRepository.findAll();
    }


}
