package com.sasinet.sasinetTask.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Saving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Reference to the user who owns the savings account

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private double interestRate; // The interest rate for the savings account
}

