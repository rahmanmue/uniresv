package com.enigmacamp.reservationcampus.services.constant.impl;

import com.enigmacamp.reservationcampus.model.entity.constant.TypeFacilities;
import com.enigmacamp.reservationcampus.repository.constant.TypeFacilitiesRepository;
import com.enigmacamp.reservationcampus.services.constant.TypeFacilitiesService;
import com.enigmacamp.reservationcampus.utils.constant.ETypeFacilities;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeFacilitiesServiceImpl implements TypeFacilitiesService {

    @Autowired
    private TypeFacilitiesRepository typeFacilitiesRepository;

    @PostConstruct
    public void initTypeFacilities() {
        if (typeFacilitiesRepository.count() == 0) {
            for (ETypeFacilities typeFacilities : ETypeFacilities.values()) {
                TypeFacilities entity = new TypeFacilities();
                entity.setName(typeFacilities);
                typeFacilitiesRepository.save(entity);
            }
        }
    }

    @Override
    public List<TypeFacilities> getAll() {
        return typeFacilitiesRepository.findAll();
    }


}
