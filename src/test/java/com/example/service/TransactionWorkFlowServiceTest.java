package com.example.service;

import com.example.TransactionAnalyticsApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TransactionAnalyticsApplication.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(value = "mock")
public class TransactionWorkFlowServiceTest {
    @Autowired
    TransactionWorkFlowService transactionWorkFlowService;

    @Test
    public void test(){
        assertTrue(transactionWorkFlowService.execute("amazon.csv", "rich123@gmail.com"));
    }
}
