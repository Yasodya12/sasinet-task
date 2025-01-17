package com.sasinet.sasinetTask.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Account account;

    private double loanAmount;
    private double interestRate;
    private double remainingAmount;
}

