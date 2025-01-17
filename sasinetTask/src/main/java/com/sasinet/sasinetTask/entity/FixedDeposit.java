package com.sasinet.sasinetTask.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class FixedDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Account account;

    private double depositAmount;
    private double interestRate;
    private LocalDateTime startDate;
    private LocalDateTime maturityDate;
}

