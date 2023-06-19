package com.example.service;

import java.io.InputStream;

public interface TransactionLoadService {
    void loadTransactionsFromCsvFile(String fileName);

    /**
     * This method is intended to be used when loading data from the company platform
     * @param in
     */
    void loadTransactionsFromInputStream(InputStream in);
}
