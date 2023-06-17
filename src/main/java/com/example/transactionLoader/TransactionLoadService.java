package com.example.transactionLoader;

public interface TransactionLoadService {
    void loadTransactionsFromCsvFile(String fileName);
    void loadTransactionsFromInputStream(String fileName);
}
