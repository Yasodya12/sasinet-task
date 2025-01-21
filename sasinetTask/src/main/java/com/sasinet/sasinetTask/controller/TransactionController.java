package com.sasinet.sasinetTask.controller;

import com.sasinet.sasinetTask.DTO.FixedDepositeDTO;
import com.sasinet.sasinetTask.DTO.TransactionDTO;
import com.sasinet.sasinetTask.service.FixedDepositeService;
import com.sasinet.sasinetTask.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FixedDepositeService fixedDepositeService;

    // End point to deposit money
    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<TransactionDTO> deposit(@PathVariable Long accountId, @RequestParam double amount) {
        System.out.println(accountId+" accout id is "+amount);
        TransactionDTO transactionDTO = transactionService.deposit(accountId, amount);
        return ResponseEntity.ok(transactionDTO);
    }
    // End point to withdraw money
    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<TransactionDTO> withdraw(@PathVariable Long accountId, @RequestParam double amount) {
        TransactionDTO transactionDTO = transactionService.withdraw(accountId, amount);
        return ResponseEntity.ok(transactionDTO);
    }
    //  End point to repay the loan
    @PostMapping("/loan/repay")
    public ResponseEntity<TransactionDTO> repayLoan(
            @RequestParam Long accountId,
            @RequestParam double repaymentAmount) {

        TransactionDTO transactionDTO = transactionService.repayLoan(accountId, repaymentAmount);
        return ResponseEntity.ok(transactionDTO);
    }
    //  End point to get fixed deposit details
    @GetMapping("/fixed-deposit/details")
    public ResponseEntity<FixedDepositeDTO> getFixedDepositDetails(@RequestParam Long accountId) {
        FixedDepositeDTO fixedDepositDetails = fixedDepositeService.getFixedDepositDetails(accountId);
        return ResponseEntity.ok(fixedDepositDetails);
    }
    //  End point to get all FD of user
    @GetMapping("/details")
    public ResponseEntity<List<FixedDepositeDTO>> getFixedDepositDetailsOfUSer(@RequestParam Long userId) {
        System.out.println("inside controller");

            List<FixedDepositeDTO> fixedDepositDetails = fixedDepositeService.getFDListByUserID(userId);
            return ResponseEntity.ok(fixedDepositDetails);

    }

    @GetMapping("/trasaction/user")
    public ResponseEntity<List<TransactionDTO>> getTransactionListByUser(@RequestParam Long userID) {
        List<TransactionDTO> transactionListUser = transactionService.getTransactionListUser(userID);
        return ResponseEntity.ok(transactionListUser);
    }
}
