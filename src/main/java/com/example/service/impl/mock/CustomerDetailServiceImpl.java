package com.example.service.impl.mock;

import com.example.model.CATEGORY;
import com.example.model.CustomerDetailDto;
import com.example.service.CustomerDetailService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Profile("mock")
public class CustomerDetailServiceImpl implements CustomerDetailService {

    @Override
    public Mono<CustomerDetailDto> getCustomerDetails(String email) {
        return Mono.just(new CustomerDetailDto("cust123", "rich123@gmail.com", "Ricahrd S", CATEGORY.MARKETING_PROMO_1));
    }
}
