package com.sasinet.sasinetTask.service.impl;

import com.sasinet.sasinetTask.DTO.SavingDTO;
import com.sasinet.sasinetTask.Repositry.SavingRepositry;
import com.sasinet.sasinetTask.entity.Saving;
import com.sasinet.sasinetTask.service.SavingAccoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SavingAccoutServiceImpl implements SavingAccoutService {

    @Autowired
    SavingRepositry savingRepositry;

    //    This funtion can get all the saving accounts of user by user ID
    @Override
    public List<SavingDTO> getSavingByUser(Long userID) {
        // Fetch the savings accounts by user ID
        List<Saving> byAccountUserId = savingRepositry.findByAccount_User_Id(userID);

        // Convert the list of Saving to SavingDTO
        List<SavingDTO> savingDTOs = byAccountUserId.stream().map(saving -> {
            SavingDTO savingDTO = new SavingDTO();

            // Set values in the SavingDTO
            savingDTO.setId(saving.getId());
            savingDTO.setAccount(saving.getAccount()); // Assuming Account has getId() method
            savingDTO.setBalance(saving.getBalance());
            savingDTO.setCreatedDate(saving.getCreatedDate());
            savingDTO.setInterestRate(saving.getInterestRate());

            return savingDTO;
        }).collect(Collectors.toList());

        // Return the list of SavingDTO
        return savingDTOs;

    }
    // Helper method to calculate the number of years elapsed since the start date
    @Override
    public List<Long> getSavingActIDByUser(Long id) {
       return savingRepositry.findAccountIdsByUserId(id);
    }
}
