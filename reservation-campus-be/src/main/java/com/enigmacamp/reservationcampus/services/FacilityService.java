package com.enigmacamp.reservationcampus.services;

import com.enigmacamp.reservationcampus.model.entity.Facility;
import com.enigmacamp.reservationcampus.model.request.FacilityRequest;
import com.enigmacamp.reservationcampus.model.response.FacilityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface FacilityService {

    FacilityRequest saveFacility(FacilityRequest facilityRequest);

    FacilityResponse updateFaciliyRes(FacilityRequest facilityRequest);

    Facility getFacilityById(String id);

    Facility updateFacility(Facility facility);

    List<Facility> getAllFacilities();

    Page<Facility> getAvailableFacilities(Date startDate, Date endDate, Pageable pageable);

    Page<Facility> getUnavailableFacilities(Date startDate, Date endDate, Pageable pageable);

    Page<Facility> getAvailableFacilitiesByName(String name, Date startDate, Date endDate, Pageable pageable);

    Page<Facility> getUnavailableFacilitiesByName(String name, Date startDate, Date endDate, Pageable pageable);

    Page<Facility> getAvailableFacilitiesByType(String typeId, Date startDate, Date endDate, Pageable pageable);

    Page<Facility> getUnavailableFacilitiesByType(String typeId, Date startDate, Date endDate, Pageable pageable);


//    List<Facility> getFacilitiesByType(String typeId);
//
//    List<Facility> getFacilityByName(String name);

    void deleteFacility(String id);

}
