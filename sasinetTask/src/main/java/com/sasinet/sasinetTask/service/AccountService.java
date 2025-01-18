package com.sasinet.sasinetTask.service;

import com.sasinet.sasinetTask.DTO.AccountDTO;


public interface AccountService {
    AccountDTO createAccount(Long userId, AccountDTO accountDTO) throws Exception;
    AccountDTO getAccountBalance(Long userId, Long accountId);
}
