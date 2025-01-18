package com.sasinet.sasinetTask.controller;

import com.sasinet.sasinetTask.DTO.FixedDepositeDTO;
import com.sasinet.sasinetTask.DTO.TransactionDTO;
import com.sasinet.sasinetTask.service.FixedDepositeService;
import com.sasinet.sasinetTask.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FixedDepositeService fixedDepositeService;

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<TransactionDTO> deposit(@PathVariable Long accountId, @RequestParam double amount) {
        System.out.println(accountId+" accout id is "+amount);
        TransactionDTO transactionDTO = transactionService.deposit(accountId, amount);
        return ResponseEntity.ok(transactionDTO);
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<TransactionDTO> withdraw(@PathVariable Long accountId, @RequestParam double amount) {
        TransactionDTO transactionDTO = transactionService.withdraw(accountId, amount);
        return ResponseEntity.ok(transactionDTO);
    }

    @PostMapping("/loan/repay")
    public ResponseEntity<TransactionDTO> repayLoan(
            @RequestParam Long accountId,
            @RequestParam double repaymentAmount) {
        TransactionDTO transactionDTO = transactionService.repayLoan(accountId, repaymentAmount);
        return ResponseEntity.ok(transactionDTO);
    }

    @GetMapping("/fixed-deposit/details")
    public ResponseEntity<FixedDepositeDTO> getFixedDepositDetails(@RequestParam Long accountId) {
        FixedDepositeDTO fixedDepositDTO = fixedDepositeService.getFixedDepositDetails(accountId);
        return ResponseEntity.ok(fixedDepositDTO);
    }
}
