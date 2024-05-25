package com.enigmacamp.reservationcampus.services;

import java.util.List;
import java.util.Map;

public interface AnalyticsService {

    Map<String, Long> getFaciltiesCountByType();

    Map<String, Long> getFaciltiesAvailability();

    Map<String, Long> getTransactionCountByStatus();



}
