package com.sasinet.sasinetTask.DTO;

import com.sasinet.sasinetTask.entity.Account;

import java.time.LocalDateTime;

public class FixedDepositeDTO {

    private Long id;


    private Account account;

    private double depositAmount;
    private double interestRate;
    private double total;
    private LocalDateTime startDate;
    private LocalDateTime maturityDate;

    public FixedDepositeDTO() {
    }

    public FixedDepositeDTO(Long id, Account account, double depositAmount, double interestRate, double total, LocalDateTime startDate, LocalDateTime maturityDate) {
        this.id = id;
        this.account = account;
        this.depositAmount = depositAmount;
        this.interestRate = interestRate;
        this.total = total;
        this.startDate = startDate;
        this.maturityDate = maturityDate;
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

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDateTime maturityDate) {
        this.maturityDate = maturityDate;
    }

    @Override
    public String toString() {
        return "FixedDepositeDTO{" +
                "id=" + id +
                ", account=" + account +
                ", depositAmount=" + depositAmount +
                ", interestRate=" + interestRate +
                ", total=" + total +
                ", startDate=" + startDate +
                ", maturityDate=" + maturityDate +
                '}';
    }
}
