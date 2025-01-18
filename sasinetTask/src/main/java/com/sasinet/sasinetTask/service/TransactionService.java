package com.sasinet.sasinetTask.service;

import com.sasinet.sasinetTask.DTO.TransactionDTO;
import com.sasinet.sasinetTask.entity.Transaction;

public interface TransactionService {
     TransactionDTO withdraw(Long accountId, double amount);
     TransactionDTO deposit(Long accountId, double amount);

     TransactionDTO repayLoan(Long accountId, double repaymentAmount);
}
