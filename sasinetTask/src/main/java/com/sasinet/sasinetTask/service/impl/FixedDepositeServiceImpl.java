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

    // Helper method to calculate the number of years elapsed since the start date
    private double getElapsedYears(LocalDateTime startDate) {
        LocalDateTime now = LocalDateTime.now();
        return java.time.Duration.between(startDate, now).toDays() / 365.25; // Approximate year calculation
    }
}
