package com.example.service.impl;

import com.example.transactionLoader.TransactionLoadService;
import com.example.service.CustomerDetailService;
import com.example.service.CustomerAnalyticsService;
import com.example.service.TransactionWorkFlowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionWorkFlowServiceImpl implements TransactionWorkFlowService {
    public final CustomerDetailService customerDetailService;
    public final TransactionLoadService transactionLoadService;
    public final CustomerAnalyticsService customerAnalyticsService;

    @Override
    public void execute(String csvFileName, String email) {
        transactionLoadService.loadTransactionsFromCsvFile(csvFileName);
        customerDetailService.getCustomerDetails(email).doOnNext(
                customerDetail->customerAnalyticsService.postAnalyticsData(customerDetail)
                        .doOnNext(Void->log.info("Successfully post transaction data. customer={}", email))
                        .doOnError(e->log.error("Error encountered while posting transaction data. email={}", email, e))
                        .subscribe()
        ).doOnError(e->
            log.error("Error encountered while getting customer details. email={}", email, e)
        ).subscribe();
    }
}
