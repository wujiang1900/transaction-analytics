package com.example.repo.entity;

import com.example.model.CURRENCY;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransactionDetail {

    @Id
    @Generated
    private Long id;

    private String customerEmail;
    private CURRENCY currency;
    private double amount;
    private String transactionId;
    private LocalDateTime ts;
    String products;
    private String vendorId;
}
