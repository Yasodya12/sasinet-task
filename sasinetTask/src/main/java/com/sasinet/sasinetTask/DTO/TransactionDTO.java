package com.sasinet.sasinetTask.DTO;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private Long accountId;
    private double amount;
    private String type; // "Deposit" or "Withdraw"
    private LocalDateTime date;

    public TransactionDTO(Long id, Long accountId, double amount, String type, LocalDateTime date) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}