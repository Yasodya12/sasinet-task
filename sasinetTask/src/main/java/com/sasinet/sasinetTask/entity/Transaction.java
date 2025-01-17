package com.sasinet.sasinetTask.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;

    private double amount;
    private String type; // "Deposit" or "Withdraw"
    private LocalDateTime date;
}

