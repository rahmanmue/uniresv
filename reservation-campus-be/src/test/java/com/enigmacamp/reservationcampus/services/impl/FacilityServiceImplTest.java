package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.model.entity.Facility;
import com.enigmacamp.reservationcampus.model.entity.constant.Availability;
import com.enigmacamp.reservationcampus.model.entity.constant.TypeFacilities;
import com.enigmacamp.reservationcampus.model.request.FacilityRequest;
import com.enigmacamp.reservationcampus.model.response.FacilityResponse;
import com.enigmacamp.reservationcampus.repository.FacilityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FacilityServiceImplTest {

    @Mock
    private FacilityRepository facilityRepository;

    @InjectMocks
    private FacilityServiceImpl facilityService;

    private Facility facility;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        facility = new Facility();
        facility.setId("1");
        facility.setName("Example Facility");
        facility.setInformation("This is an example facility");
        facility.setPrice(10000);
        facility.setQuantity(5);
        facility.setPicture("example.jpg");
        facility.setAvailability(Availability.builder().id("AVAILABLE").build());
        facility.setTypeFacilities(TypeFacilities.builder().id("TYPE_A").build());
    }

    @Test
    public void testSaveFacility() {
        FacilityRequest facilityRequest = new FacilityRequest();
        facilityRequest.setName("Example Facility");
        facilityRequest.setInformation("This is an example facility");
        facilityRequest.setPrice(10000);
        facilityRequest.setQuantity(5);
        facilityRequest.setPicture("example.jpg");
        facilityRequest.setAvailability("AVAILABLE");
        facilityRequest.setTypeFacilities("TYPE_A");

        when(facilityRepository.save(any())).thenReturn(facility);

        FacilityRequest savedFacility = facilityService.saveFacility(facilityRequest);

        assertEquals(facility.getName(), savedFacility.getName());
        assertEquals(facility.getInformation(), savedFacility.getInformation());
        assertEquals(facility.getPrice(), savedFacility.getPrice());
        assertEquals(facility.getQuantity(), savedFacility.getQuantity());
        assertEquals(facility.getPicture(), savedFacility.getPicture());
    }

    @Test
    public void testUpdateFacility() {
        FacilityRequest facilityRequest = new FacilityRequest();
        facilityRequest.setId("1");
        facilityRequest.setName("Updated Facility");
        facilityRequest.setInformation("Updated facility information");
        facilityRequest.setPrice(20000);
        facilityRequest.setQuantity(10);
        facilityRequest.setPicture("updated.jpg");
        facilityRequest.setAvailability("UNAVAILABLE");
        facilityRequest.setTypeFacilities("TYPE_B");

        when(facilityRepository.findById("1")).thenReturn(Optional.of(facility));
        when(facilityRepository.save(any())).thenReturn(facility);

        FacilityResponse updatedFacility = facilityService.updateFaciliyRes(facilityRequest);

        assertEquals(facilityRequest.getName(), updatedFacility.getName());
        assertEquals(facilityRequest.getTypeFacilities(), updatedFacility.getTypeFacilities());
    }

    @Test
    public void testGetFacilityById() {
        when(facilityRepository.findById("1")).thenReturn(Optional.of(facility));

        Facility retrievedFacility = facilityService.getFacilityById("1");

        assertEquals(facility.getId(), retrievedFacility.getId());
        assertEquals(facility.getName(), retrievedFacility.getName());
    }

    @Test
    public void testGetAllFacilities() {
        List<Facility> facilityList = new ArrayList<>();
        facilityList.add(facility);

        when(facilityRepository.findAll()).thenReturn(facilityList);

        List<Facility> retrievedFacilities = facilityService.getAllFacilities();

        assertEquals(facilityList.size(), retrievedFacilities.size());
    }

    // Add more test cases for other methods if needed
}
