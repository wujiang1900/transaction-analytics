package com.example.service;

import com.example.model.CustomerDetailDto;

public interface CustomerDetailService {
    CustomerDetailDto getCustomerDetails(String email);
}
