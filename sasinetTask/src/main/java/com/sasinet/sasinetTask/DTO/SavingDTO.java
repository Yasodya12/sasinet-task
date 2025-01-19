package com.sasinet.sasinetTask.DTO;

import com.sasinet.sasinetTask.entity.Account;

import java.time.LocalDateTime;

public class SavingDTO {
    private Long id;


    private Account account; // Reference to the user who owns the savings account


    private double balance;


    private LocalDateTime createdDate;


    private double interestRate;

    public SavingDTO() {
    }

    public SavingDTO(Long id, Account account, double balance, LocalDateTime createdDate, double interestRate) {
        this.id = id;
        this.account = account;
        this.balance = balance;
        this.createdDate = createdDate;
        this.interestRate = interestRate;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "SavingDTO{" +
                "id=" + id +
                ", account=" + account +
                ", balance=" + balance +
                ", createdDate=" + createdDate +
                ", interestRate=" + interestRate +
                '}';
    }
}
