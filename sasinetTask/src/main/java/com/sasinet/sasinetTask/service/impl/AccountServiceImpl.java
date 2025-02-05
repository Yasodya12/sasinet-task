package com.sasinet.sasinetTask.service.impl;

import com.sasinet.sasinetTask.DTO.AccountDTO;
import com.sasinet.sasinetTask.DTO.LoanDTO;
import com.sasinet.sasinetTask.Repositry.*;
import com.sasinet.sasinetTask.entity.*;
import com.sasinet.sasinetTask.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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
    // This is the funtion that create account account can be SAVING,LOAN,FIXEDDEPOSIT
    @Override
    public AccountDTO createAccount(Long userId, AccountDTO accountDTO)  {
        // Check if user exists
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            try {
                throw new Exception("User not found");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
    // This funtion can get account balance of saving account
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
    // This funtion can get All the accounts that user have
    @Override
    public List<AccountDTO> getUserAccounts(Long userID) {
        List<Account> accountByUserId = accountRepository.findAccountByUserId(userID);
        List<AccountDTO> accountDTOs = accountByUserId.stream().map(account -> {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setId(account.getId()); // Set account ID
            accountDTO.setType(account.getType()); // Set account type (e.g., SAVING, FIXEDDEPOSITE, LOAN)
            accountDTO.setBalance(account.getBalance()); // Set account balance

            return accountDTO;
        }).collect(Collectors.toList());

        return accountDTOs;

    }

    // This funtion can get All the loans that user have
    @Override
    public List<LoanDTO> getLoanByUser(Long userID) {
        List<Loan> loanList = loanRepositry.findByAccount_User_Id(userID);

        // Convert Loan entities to LoanDTO
        List<LoanDTO> loanDTOList = loanList.stream().map(loan -> {
            // Extract data from the Loan and Account entities
            Account account = loan.getAccount();  // Assuming you have a reference to the Account object in Loan

            // Create and return the LoanDTO
            return new LoanDTO(
                    loan.getId(),
                    account,  // Account object (or its relevant part, you can modify it if you need just certain fields)
                    loan.getLoanAmount(),
                    loan.getInterestRate(),
                    loan.getRemainingAmount()
            );
        }).collect(Collectors.toList());

        return loanDTOList;
    }


    private double getElapsedYears(LocalDateTime startDate) {
        LocalDateTime now = LocalDateTime.now();
        return java.time.Duration.between(startDate, now).toDays() / 365.25; // Approximate year calculation
    }
}
