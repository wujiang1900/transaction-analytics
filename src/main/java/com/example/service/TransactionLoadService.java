package com.example.service;

public interface TransactionLoadService {
    void loadTransactionsFromCsvFile(String fileName);
    void loadTransactionsFromInputStream(String fileName);
}
