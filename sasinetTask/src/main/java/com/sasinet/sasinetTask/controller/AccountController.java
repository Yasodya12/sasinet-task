package com.sasinet.sasinetTask.controller;

import com.sasinet.sasinetTask.DTO.AccountDTO;
import com.sasinet.sasinetTask.DTO.LoanDTO;
import com.sasinet.sasinetTask.service.AccountService;
import com.sasinet.sasinetTask.util.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin
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

    @GetMapping("/{userId}/{accountId}/balance")
    public ResponseEntity<?> getAccountBalance(@PathVariable Long userId, @PathVariable Long accountId) {
        try {
            AccountDTO accountDTO = accountService.getAccountBalance(userId, accountId);
            return ResponseEntity.ok(accountDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/accountBySer/{userId}")
    public ResponseEntity<List<AccountDTO>> getAccountByUser(@PathVariable Long userId){
        List<AccountDTO> userAccounts = accountService.getUserAccounts(userId);
        return ResponseEntity.ok(userAccounts);
    }


    @GetMapping("/byUser/{userID}")
    public ResponseEntity<List<LoanDTO>> getLoansByUserId(@PathVariable Long userID) {
        List<LoanDTO> loanDTOList = accountService.getLoanByUser(userID);
        return ResponseEntity.ok(loanDTOList);
    }
}
