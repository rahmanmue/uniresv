package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.repository.AnalyticsRepository;
import com.enigmacamp.reservationcampus.services.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    @Override
    public Map<String, Long> getFaciltiesCountByType() {
        List<Object[]> results = analyticsRepository.countFacilitiesByType();
        Map<String, Long> countByType = new HashMap<>();
        for (Object[] result : results) {
            countByType.put(result[0].toString(), (Long) result[1]);
        }
        return countByType;
    }

    @Override
    public Map<String, Long> getFaciltiesAvailability() {
        List<Object[]> results = analyticsRepository.countFacilitiesByAvailability();
        Map<String, Long> countByAvailability = new HashMap<>();

        countByAvailability.put("AVAILABILITY_YES", 0L);
        countByAvailability.put("AVAILABILITY_NO", 0L);

        for (Object[] result : results) {
            countByAvailability.put(result[0].toString(), (Long) result[1]);
        }
        return countByAvailability;
    }

    @Override
    public Map<String, Long> getTransactionCountByStatus() {
        List<Object[]> results = analyticsRepository.countTransactionsByStatus();
        Map<String, Long> countByStatus = new HashMap<>();
        for (Object[] result : results) {
            countByStatus.put(result[0].toString(), (Long) result[1]);
        }
        return countByStatus;
    }
}
