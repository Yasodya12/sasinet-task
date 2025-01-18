package com.sasinet.sasinetTask.service.impl;

import com.sasinet.sasinetTask.DTO.AccountDTO;
import com.sasinet.sasinetTask.Repositry.*;
import com.sasinet.sasinetTask.entity.*;
import com.sasinet.sasinetTask.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private final LoanRepositry loanRepositry;

    private final SavingRepositry savingRepositry;

    private final FixedDepositeRepositry fixedDepositeRepositry;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository, LoanRepositry loanRepositry, SavingRepositry savingRepositry, FixedDepositeRepositry fixedDepositeRepositry) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.loanRepositry = loanRepositry;
        this.savingRepositry = savingRepositry;
        this.fixedDepositeRepositry = fixedDepositeRepositry;
    }

    @Override
    public AccountDTO createAccount(Long userId, AccountDTO accountDTO) throws Exception {
        // Check if user exists
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new Exception("User not found");
        }

        // Create the account
        Account account = new Account();
        account.setType(accountDTO.getType());
        account.setBalance(accountDTO.getBalance());
        System.out.println("before this");
        account.setUser(user.get());
        System.out.println("after this"+user.get());
        // Save and return the account with ID
        account = accountRepository.save(account);
        System.out.println("after save");
        String type = accountDTO.getType();
        switch (type) {
            case "SAVING":
                Saving saving = new Saving();
                Account account2 = accountRepository.findById(account.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Account not found with id: "));
                saving.setAccount(account2);
                saving.setBalance(accountDTO.getBalance());
                saving.setCreatedDate(LocalDateTime.now());
                saving.setInterestRate(0);
                System.out.println(saving);
                savingRepositry.save(saving);
                System.out.println("save done");
                break;
            case "FIXEDDEPOSITE":
                FixedDeposit fixedDeposit = new FixedDeposit();

// Fetch account
                Account account3 = accountRepository.findById(account.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Account not found with id: "));

// Set account and balance for the fixed deposit
                fixedDeposit.setAccount(account3);
                fixedDeposit.setDepositAmount(accountDTO.getBalance());
                fixedDeposit.setInterestRate(5);  // Assuming a fixed interest rate of 5%

// Set start date (current date)
                LocalDateTime startDate = LocalDateTime.now();
                fixedDeposit.setStartDate(startDate);

// Calculate interest earned based on deposit amount and interest rate
                double interestEarned = (fixedDeposit.getDepositAmount() * fixedDeposit.getInterestRate() * getElapsedYears(startDate)) / 100;

// Set the total amount (principal + interest earned)
                double totalAmount = fixedDeposit.getDepositAmount() + interestEarned;
                fixedDeposit.setTotal(totalAmount);

// Save the fixed deposit to the repository
                fixedDepositeRepositry.save(fixedDeposit);
                break;
            case "LOAN":
                Loan loan = new Loan();
                Account account4 = accountRepository.findById(account.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Account not found with id: "));
                loan.setAccount(account4);
                loan.setLoanAmount(accountDTO.getBalance());
                loan.setInterestRate(10);
                loan.setRemainingAmount(accountDTO.getBalance()+accountDTO.getBalance()*10/100);
                loanRepositry.save(loan);
                break;
            default:
                throw new IllegalArgumentException("Invalid Account Type");
        }
        // Map Account entity to AccountDTO to return
        AccountDTO responseDTO = new AccountDTO();
        responseDTO.setId(account.getId());
        responseDTO.setType(account.getType());
        responseDTO.setBalance(account.getBalance());

        return responseDTO;
    }

    @Override
    public AccountDTO getAccountBalance(Long userId, Long accountId) {
        // Validate the user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Validate the account exists and belongs to the user
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + accountId));

        if (!account.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Account does not belong to the specified user");
        }

        // Map to DTO and return only necessary details
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setType(account.getType());
        accountDTO.setBalance(account.getBalance());

        return accountDTO;
    }

    private double getElapsedYears(LocalDateTime startDate) {
        LocalDateTime now = LocalDateTime.now();
        return java.time.Duration.between(startDate, now).toDays() / 365.25; // Approximate year calculation
    }
}
