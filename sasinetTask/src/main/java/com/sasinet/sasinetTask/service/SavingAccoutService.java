package com.sasinet.sasinetTask.service;

import com.sasinet.sasinetTask.DTO.LoanDTO;
import com.sasinet.sasinetTask.DTO.SavingDTO;

import java.util.List;

public interface SavingAccoutService {

     List<SavingDTO> getSavingByUser(Long userID);

     List<Long> getSavingActIDByUser(Long id);

}
