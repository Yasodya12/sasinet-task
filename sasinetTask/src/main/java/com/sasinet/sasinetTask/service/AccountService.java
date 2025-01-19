package com.sasinet.sasinetTask.service;

import com.sasinet.sasinetTask.DTO.AccountDTO;
import com.sasinet.sasinetTask.DTO.LoanDTO;
import com.sasinet.sasinetTask.entity.Loan;

import java.util.List;


public interface AccountService {
    AccountDTO createAccount(Long userId, AccountDTO accountDTO) throws Exception;
    AccountDTO getAccountBalance(Long userId, Long accountId);

    List<AccountDTO> getUserAccounts(Long userID);

    List<LoanDTO> getLoanByUser(Long userID);
}
