package com.example.service.impl;

import com.example.transactionLoader.TransactionLoadService;
import com.example.model.CustomerDetailDto;
import com.example.service.CustomerDetailService;
import com.example.service.CustomerAnalyticsService;
import com.example.service.TransactionWorkFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionWorkFlowServiceImpl implements TransactionWorkFlowService {
    public final CustomerDetailService customerDetailService;
    public final TransactionLoadService transactionLoadService;
    public final CustomerAnalyticsService customerAnalyticsService;

    @Override
    public void execute(String csvFileName, String email) {
        transactionLoadService.loadTransactionsFromCsvFile(csvFileName);
        CustomerDetailDto customerDetailDto = customerDetailService.getCustomerDetails(email);
        customerAnalyticsService.postAnalyticsData(customerDetailDto);
    }
}
