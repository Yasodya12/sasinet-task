package com.sasinet.sasinetTask.service;

import com.sasinet.sasinetTask.DTO.FixedDepositeDTO;

import java.util.List;

public interface FixedDepositeService {
    FixedDepositeDTO getFixedDepositDetails(Long accountId);

    List<FixedDepositeDTO> getFDListByUserID(Long userID);



}
