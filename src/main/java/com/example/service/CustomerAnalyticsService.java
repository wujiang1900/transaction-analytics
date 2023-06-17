package com.example.service;

import com.example.model.CustomerDetailDto;

public interface CustomerAnalyticsService {
    void postAnalyticsData(CustomerDetailDto customerDetailDto);
}
