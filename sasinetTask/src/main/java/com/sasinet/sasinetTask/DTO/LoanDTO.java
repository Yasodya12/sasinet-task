package com.sasinet.sasinetTask.DTO;

import com.sasinet.sasinetTask.entity.Account;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;


public class LoanDTO {

    private Long id;


    private Account account;

    private double loanAmount;
    private double interestRate;
    private double remainingAmount;

    public LoanDTO() {
    }

    public LoanDTO(Long id, Account account, double loanAmount, double interestRate, double remainingAmount) {
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

    @Override
    public String toString() {
        return "LoanDTO{" +
                "id=" + id +
                ", account=" + account +
                ", loanAmount=" + loanAmount +
                ", interestRate=" + interestRate +
                ", remainingAmount=" + remainingAmount +
                '}';
    }
}
