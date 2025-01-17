package com.sasinet.sasinetTask.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccountDTO {

    private Long id;  // Include account ID

    @NotBlank(message = "Account type is required")
    private String type; // "Loan", "Fixed Deposit", "Savings"

    @NotNull(message = "Initial deposit is required")
    private double balance;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
