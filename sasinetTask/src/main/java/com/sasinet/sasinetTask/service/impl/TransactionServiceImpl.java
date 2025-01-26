package com.sasinet.sasinetTask.service.impl;

import com.sasinet.sasinetTask.DTO.TransactionDTO;
import com.sasinet.sasinetTask.Repositry.AccountRepository;
import com.sasinet.sasinetTask.Repositry.LoanRepositry;
import com.sasinet.sasinetTask.Repositry.SavingRepositry;
import com.sasinet.sasinetTask.Repositry.TransactionRepository;
import com.sasinet.sasinetTask.entity.Account;
import com.sasinet.sasinetTask.entity.Loan;
import com.sasinet.sasinetTask.entity.Saving;
import com.sasinet.sasinetTask.entity.Transaction;
import com.sasinet.sasinetTask.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SavingRepositry savingRepositry;

    @Autowired
    private  LoanRepositry loanRepositry;

    // This is the funtion that deposit money to saving account
    @Override
    public TransactionDTO deposit(Long accountId, double amount) {
        // Fetch the account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with id: " + accountId));

        // Validate account type
        if (!"SAVING".equalsIgnoreCase(account.getType())) {
            throw new IllegalArgumentException("Deposits can only be made to Saving accounts");
        }

        // Validate deposit amount
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }

        // Update account balance
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // Update Saving entity balance
        Saving saving = savingRepositry.findByAccount(account);

        saving.setBalance(saving.getBalance() + amount);
        savingRepositry.save(saving);

        // Create a new transaction record
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType("Deposit");
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Return a TransactionDTO
        return new TransactionDTO(
                savedTransaction.getId(),
                account.getId(),
                savedTransaction.getAmount(),
                savedTransaction.getType(),
                savedTransaction.getDate()
        );
    }
    // This is the funtion that withdraw money from saving account
    @Override
    public TransactionDTO withdraw(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with id: " + accountId));

        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0");
        }

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        // Create a new transaction record
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType("Withdraw");
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(transaction);

        // Return a TransactionDTO
        return new TransactionDTO(savedTransaction.getId(), account.getId(), savedTransaction.getAmount(),
                savedTransaction.getType(), savedTransaction.getDate());
    }

    // This is the funtion that repay loan
    @Override
    public TransactionDTO repayLoan(Long accountId, double repaymentAmount) {
        // Fetch the account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with id: " ));

        // Validate account type
        if (!"LOAN".equalsIgnoreCase(account.getType())) {
            throw new IllegalArgumentException("Repayment can only be made to Loan accounts");
        }

        // Validate repayment amount
        if (repaymentAmount <= 0) {
            throw new IllegalArgumentException("Repayment amount must be greater than 0");
        }

        // Fetch the associated Loan entity
        Loan loan = loanRepositry.findByAccount(account);

        if (loan.getRemainingAmount() < repaymentAmount) {
            throw new IllegalArgumentException("Repayment amount exceeds remaining loan balance");
        }

        // Update loan remaining amount
        loan.setRemainingAmount(loan.getRemainingAmount() - repaymentAmount);
        loanRepositry.save(loan);

        // Update account balance if applicable (e.g., deduct payment from account balance)
        account.setBalance(account.getBalance() - repaymentAmount);
        accountRepository.save(account);

        // Record the repayment as a transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType("Loan Repayment");
        transaction.setAmount(repaymentAmount);
        transaction.setDate(LocalDateTime.now());
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Return a TransactionDTO
        return new TransactionDTO(
                savedTransaction.getId(),
                account.getId(),
                savedTransaction.getAmount(),
                savedTransaction.getType(),
                savedTransaction.getDate()
        );
    }
    // This is the funtion that can get all the transactions done by user
    @Override
    public List<TransactionDTO> getTransactionListUser(Long userID) {
        List<Transaction> transactions = transactionRepository.findByAccount_User_Id(userID);

        // Convert each Transaction entity to a TransactionDTO and return the list of DTOs
        return transactions.stream()
                .map(transaction -> new TransactionDTO(
                        transaction.getId(),
                        transaction.getAccount().getId(),
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getDate()))
                .collect(Collectors.toList());

    }


}
