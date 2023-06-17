package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailDto {

    private CURRENCY currency;
    private double amount;
    private String transactionId;
    private LocalDateTime ts;
    List<String> products;
    private String vendorId;
}
