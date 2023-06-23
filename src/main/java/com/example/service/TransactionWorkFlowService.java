package com.example.service;

import reactor.core.publisher.Mono;

public interface TransactionWorkFlowService {

    Mono<String> execute(String csvFileName, String email);
}
