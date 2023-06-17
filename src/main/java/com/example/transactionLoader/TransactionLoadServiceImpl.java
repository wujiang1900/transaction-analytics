package com.example.transactionLoader;

import com.example.model.CURRENCY;
import com.example.repo.TransactionRepository;
import com.example.repo.entity.TransactionDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
public class TransactionLoadServiceImpl implements TransactionLoadService{
    private final TransactionRepository repo;
    @Override
    public void loadTransactionsFromCsvFile(String fileName) {
        log.info("Loading transactions from file={}", fileName);
        var filePath = // System.getProperty("user.dir") +
                 "/resources/" + fileName;
        try {
            Files.lines(Paths.get(filePath))
                    .skip(0) // ignore the header line
                    .parallel() // utilizing parallel stream
                    .forEach(this::processOneTransaction);
        } catch (IOException e) {
            log.error("Error encountered while loading transactions from file={}", fileName, e);
            throw new RuntimeException(e);
        }
    }

    private void processOneTransaction(String s) {
        try {
            String[] col = s.split(",");
            String email = col[0];
            String products = col[1];
            String txId = col[2];
            double price = Double.parseDouble(col[3]);
            CURRENCY currency = CURRENCY.valueOf(col[4].toUpperCase());
            LocalDateTime ts = LocalDateTime.parse(col[5]);
            // We ignore coloumn "Region" for now
            String vendorId = col[7];

            repo.save(new TransactionDetail(null, email, currency, price, txId, ts, products, vendorId));
        }
        catch (RuntimeException e) {
            log.error("Failed to parse one line. line={}", s, e);
        }
    }

    @Override
    public void loadTransactionsFromInputStream(String fileName) {
        throw new RuntimeException("Not Implemented yet.");
    }
}
