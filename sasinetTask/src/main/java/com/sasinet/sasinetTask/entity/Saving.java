package com.sasinet.sasinetTask.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity

public class Saving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accnt_id", nullable = false)
    private Account account; // Reference to the user who owns the savings account

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private double interestRate; // The interest rate for the savings account

    public Saving() {
    }

    public Saving(Long id, Account account, double balance, LocalDateTime createdDate, double interestRate) {
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
        return "Saving{" +
                "id=" + id +
                ", account=" + account +
                ", balance=" + balance +
                ", createdDate=" + createdDate +
                ", interestRate=" + interestRate +
                '}';
    }
}

