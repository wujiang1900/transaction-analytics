package com.example.service.impl;

import com.example.model.CustomerDetailDto;
import com.example.service.CustomerDetailService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class CustomerDetailServiceImpl implements CustomerDetailService {
    private final WebClient webClient;
    private final String path;

    public CustomerDetailServiceImpl(@Value("customerdetail.host") String customerDetailHost,
                                      @Value("customerdetail.path") String path) {
        webClient = WebClient.create(customerDetailHost);
        this.path = path;
    }
    @Override
    public Mono<CustomerDetailDto> getCustomerDetails(String email) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("email", email)
                        .build())
                .header("X-API", "ABC123")
                .retrieve()
                .bodyToMono(CustomerDetailResponse.class)
                .map(CustomerDetailResponse::getCustomer);
    }
    @NoArgsConstructor
    @Data
    private class CustomerDetailResponse {
        String code;
        CustomerDetailDto customer;
    }
}
