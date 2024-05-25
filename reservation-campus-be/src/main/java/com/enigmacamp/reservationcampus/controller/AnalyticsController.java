package com.enigmacamp.reservationcampus.controller;


import com.enigmacamp.reservationcampus.model.response.CommonResponse;
import com.enigmacamp.reservationcampus.services.AnalyticsService;
import com.enigmacamp.reservationcampus.utils.constant.APIPath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(APIPath.API + "/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping(APIPath.FACILITIES + "/countByType")
    public ResponseEntity<Map<String, Long>> getFacilityCountByType(){
        return ResponseEntity.ok(analyticsService.getFaciltiesCountByType());
    }

    @GetMapping(APIPath.FACILITIES + "/countByAvailability")
    public ResponseEntity<Map<String, Long>> getFacilityCountByAvailability(){
        Map<String, Long> counts = analyticsService.getFaciltiesAvailability();
        return ResponseEntity.ok(counts);
    }

    @GetMapping(APIPath.TRANSACTION + "/countByStatus")
    public ResponseEntity<Map<String, Long>> getTransactionCountByStatus(){
        return ResponseEntity.ok(analyticsService.getTransactionCountByStatus());
    }
}
