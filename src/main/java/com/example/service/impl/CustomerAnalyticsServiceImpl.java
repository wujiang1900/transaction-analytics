package com.example.service.impl;

import com.example.model.CATEGORY;
import com.example.model.CustomerDetailDto;
import com.example.model.TransactionDetailDto;
import com.example.repo.TransactionRepository;
import com.example.service.CustomerAnalyticsService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile({"default", "mock"})
public class CustomerAnalyticsServiceImpl implements CustomerAnalyticsService {
    private final WebClient webClient;
    private final String path;
    private final TransactionRepository repo;
    private final ModelMapper mapper;
    public CustomerAnalyticsServiceImpl(@Value("customeranalytics.host") String host,
                                         @Value("customeranalytics.path") String path,
                                         TransactionRepository repo) {
        webClient = WebClient.create(host);
        this.path = path;
        this.repo = repo;
        this.mapper = new ModelMapper();
    }

    @Override
    public Mono<Void> postAnalyticsData(final CustomerDetailDto customerDetailDto) {
        List<TransactionDetailDto> transactions = repo.findByCustomerEmailIgnoreCase(customerDetailDto.getEmail())
                        .parallelStream()
                        .map(entity->mapper.map(entity, TransactionDetailDto.class))
                        .collect(Collectors.toList());
        double totalAmount = transactions.parallelStream().mapToDouble(TransactionDetailDto::getAmount).sum();

        CustomerAnalyticsRequest request = CustomerAnalyticsRequest.builder()
                .custId(customerDetailDto.getId())
                .custCategory(customerDetailDto.getCategory())
                .transaction_summary(new TransactionSummary(transactions.size(), totalAmount))
                .transactions(transactions)
                .build();

        return webClient.post()
                .uri(path)
                .header("X-API", "ABC123")
                .body(Mono.just(request), CustomerAnalyticsRequest.class)
                .retrieve()
                .bodyToMono(Void.class)
                .then();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    private static class CustomerAnalyticsRequest {
        String custId;
        CATEGORY custCategory;
        TransactionSummary transaction_summary;
        List<TransactionDetailDto> transactions;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TransactionSummary {
        int total_count;
        double total_amount;
    }
}
