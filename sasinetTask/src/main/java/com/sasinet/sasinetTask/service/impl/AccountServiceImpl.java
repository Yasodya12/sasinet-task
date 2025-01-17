package com.sasinet.sasinetTask.service.impl;

import com.sasinet.sasinetTask.DTO.AccountDTO;
import com.sasinet.sasinetTask.Repositry.AccountRepository;
import com.sasinet.sasinetTask.Repositry.UserRepository;
import com.sasinet.sasinetTask.entity.Account;
import com.sasinet.sasinetTask.entity.User;
import com.sasinet.sasinetTask.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
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
        account.setUser(user.get());

        // Save and return the account with ID
        account = accountRepository.save(account);

        // Map Account entity to AccountDTO to return
        AccountDTO responseDTO = new AccountDTO();
        responseDTO.setId(account.getId());
        responseDTO.setType(account.getType());
        responseDTO.setBalance(account.getBalance());

        return responseDTO;
    }
}
