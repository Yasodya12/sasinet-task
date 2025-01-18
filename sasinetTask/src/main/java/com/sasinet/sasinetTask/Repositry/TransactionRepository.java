package com.sasinet.sasinetTask.Repositry;

import com.sasinet.sasinetTask.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
