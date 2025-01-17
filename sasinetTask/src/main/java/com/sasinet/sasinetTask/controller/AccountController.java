package com.sasinet.sasinetTask.controller;

import com.sasinet.sasinetTask.DTO.AccountDTO;
import com.sasinet.sasinetTask.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Create Account Endpoint
    @PostMapping("/create/{userId}")
    public ResponseEntity<AccountDTO> createAccount(@PathVariable Long userId, @Valid @RequestBody AccountDTO accountDTO) {
        System.out.println(userId);
        try {
            AccountDTO responseDTO = accountService.createAccount(userId, accountDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
