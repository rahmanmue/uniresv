package com.enigmacamp.reservationcampus.controller.constant;

import com.enigmacamp.reservationcampus.model.entity.constant.Availability;
import com.enigmacamp.reservationcampus.services.constant.AvailabilityService;
import com.enigmacamp.reservationcampus.utils.constant.APIPath;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIPath.API + APIPath.AVAILABILITY)
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @GetMapping
    public ResponseEntity<List<Availability>> getAllAvailability() {
        List<Availability> availabilityList = availabilityService.getAll();
        return ResponseEntity.ok(availabilityList);
    }
}
