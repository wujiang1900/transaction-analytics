package com.example.service.impl.mock;

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
@Profile({"mock"})
public class CustomerAnalyticsServiceImpl implements CustomerAnalyticsService {

    private final TransactionRepository repo;
    private final ModelMapper mapper;
    public CustomerAnalyticsServiceImpl(TransactionRepository repo) {
        this.repo = repo;
        this.mapper = new ModelMapper();
    }

    @Override
    public Mono<Void> postAnalyticsData(final CustomerDetailDto customerDetailDto) {
        return Mono.empty();
    }
}
