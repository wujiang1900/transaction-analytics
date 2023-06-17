package com.example.service;

import com.example.model.CustomerDetailDto;
import reactor.core.publisher.Mono;

public interface CustomerAnalyticsService {
    Mono<Void> postAnalyticsData(CustomerDetailDto customerDetailDto);
}
