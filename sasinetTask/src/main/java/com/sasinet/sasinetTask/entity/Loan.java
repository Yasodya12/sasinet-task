package com.sasinet.sasinetTask.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Account account;

    private double loanAmount;
    private double interestRate;
    private double remainingAmount;

    public Loan() {
    }

    public Loan(Long id, Account account, double loanAmount, double interestRate, double remainingAmount) {
        this.id = id;
        this.account = account;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.remainingAmount = remainingAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}

