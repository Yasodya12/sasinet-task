package com.sasinet.sasinetTask.service.impl;

import com.sasinet.sasinetTask.DTO.FixedDepositeDTO;
import com.sasinet.sasinetTask.Repositry.AccountRepository;
import com.sasinet.sasinetTask.Repositry.FixedDepositeRepositry;
import com.sasinet.sasinetTask.entity.Account;
import com.sasinet.sasinetTask.entity.FixedDeposit;
import com.sasinet.sasinetTask.service.FixedDepositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FixedDepositeServiceImpl implements FixedDepositeService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    FixedDepositeRepositry fixedDepositeRepositry;
    @Override
    public FixedDepositeDTO getFixedDepositDetails(Long accountId) {
        // Fetch the account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with id: " + accountId));

        // Validate account type
        if (!"FIXEDDEPOSITE".equalsIgnoreCase(account.getType())) {
            throw new IllegalArgumentException("Only Fixed Deposit accounts can be checked for details");
        }

        // Fetch the associated Fixed Deposit entity
        FixedDeposit fixedDeposit = fixedDepositeRepositry.findByAccount(account);

        // Calculate interest earned (Simple Interest = P * R * T / 100)
        double interestEarned = (fixedDeposit.getDepositAmount() * fixedDeposit.getInterestRate() * getElapsedYears(fixedDeposit.getStartDate())) / 100;

        // Prepare the FixedDepositDTO response
        FixedDepositeDTO responseDTO = new FixedDepositeDTO();
        responseDTO.setId(fixedDeposit.getId());
        responseDTO.setAccount(fixedDeposit.getAccount());
        responseDTO.setDepositAmount(fixedDeposit.getDepositAmount());
        responseDTO.setInterestRate(fixedDeposit.getInterestRate());
        responseDTO.setStartDate(fixedDeposit.getStartDate());
        responseDTO.setMaturityDate(fixedDeposit.getStartDate().plusYears(5)); // Assuming a 5-year maturity
        responseDTO.setTotal(interestEarned+fixedDeposit.getDepositAmount());

        return responseDTO;
    }

    @Override
    public List<FixedDepositeDTO> getFDListByUserID(Long userID) {
        List<Account> accountByUserId = accountRepository.findAccountByUserId(userID);

        // Filter for only "FIXEDDEPOSITE" type accounts
        List<Account> fixedDepositAccounts = accountByUserId.stream()
                .filter(account -> "FIXEDDEPOSITE".equalsIgnoreCase(account.getType()))
                .collect(Collectors.toList());

        if (fixedDepositAccounts.isEmpty()) {
            throw new IllegalArgumentException("No Fixed Deposit accounts found for the user");
        }

        // Process each fixed deposit account and convert to DTO
        List<FixedDepositeDTO> fixedDepositDetails = fixedDepositAccounts.stream()
                .map(account -> {
                    // Retrieve the fixed deposit for the account
                    FixedDeposit fixedDeposit = fixedDepositeRepositry.findByAccount(account);

                    // Calculate interest earned
                    double interestEarned = (fixedDeposit.getDepositAmount() * fixedDeposit.getInterestRate() * getElapsedYears(fixedDeposit.getStartDate())) / 100;

                    // Create the DTO
                    FixedDepositeDTO responseDTO = new FixedDepositeDTO();
                    responseDTO.setId(fixedDeposit.getId());
                    responseDTO.setAccount(fixedDeposit.getAccount());
                    responseDTO.setDepositAmount(fixedDeposit.getDepositAmount());
                    responseDTO.setInterestRate(fixedDeposit.getInterestRate());
                    responseDTO.setStartDate(fixedDeposit.getStartDate());
                    responseDTO.setMaturityDate(fixedDeposit.getStartDate().plusYears(5)); // Assuming a 5-year maturity
                    responseDTO.setTotal(interestEarned + fixedDeposit.getDepositAmount());

                    return responseDTO;
                })
                .collect(Collectors.toList());

        return fixedDepositDetails;
    }

    // Helper method to calculate the number of years elapsed since the start date
    private double getElapsedYears(LocalDateTime startDate) {
        LocalDateTime now = LocalDateTime.now();
        return java.time.Duration.between(startDate, now).toDays() / 365.25; // Approximate year calculation
    }
}
