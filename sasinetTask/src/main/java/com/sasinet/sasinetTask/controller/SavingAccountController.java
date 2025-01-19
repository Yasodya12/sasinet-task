package com.sasinet.sasinetTask.controller;

import com.sasinet.sasinetTask.DTO.SavingDTO;
import com.sasinet.sasinetTask.service.SavingAccoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savingaccount")
@CrossOrigin
public class SavingAccountController {
    @Autowired
    private SavingAccoutService savingService;

    // Endpoint to get all savings by user ID
    @GetMapping("/byUser")
    public List<SavingDTO> getSavingsByUser(@RequestParam("userId") Long userId) {
        // Call the service method to fetch the savings and return the DTO list
        return savingService.getSavingByUser(userId);
    }
    @GetMapping("/savings/accountIds")
    public List<Long> getAccountIdsByUserId(@RequestParam Long userId) {
        return savingService.getSavingActIDByUser(userId);
    }
}
