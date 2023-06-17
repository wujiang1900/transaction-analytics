package com.example.service.impl;

import com.example.model.CustomerDetailDto;
import com.example.service.CustomerDetailService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class CustomerDetailServiceImpl implements CustomerDetailService {
    private final WebClient webClient;
    private final String path;

    public CustomerDetailServiceImpl(@Value("customerdetail.host") String customerDetailHost,
                                      @Value("customerdetail.path") String path) {
        webClient = WebClient.create(customerDetailHost);
        this.path = path;
    }
    @Override
    public CustomerDetailDto getCustomerDetails(String email) {
        CustomerDetailResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("email", email)
                        .build())
                .header("X-API", "ABC123")
                .retrieve()
                .bodyToMono(CustomerDetailResponse.class)
                .block();
        return response.customer;
    }
    @NoArgsConstructor
    @Data
    private class CustomerDetailResponse {
        String code;
        CustomerDetailDto customer;
    }
}
