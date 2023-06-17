package com.example.service;

import com.example.model.CustomerDetailDto;
import reactor.core.publisher.Mono;

public interface CustomerDetailService {
    Mono<CustomerDetailDto> getCustomerDetails(String email);
}
