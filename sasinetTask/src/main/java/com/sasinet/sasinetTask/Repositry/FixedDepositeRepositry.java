package com.sasinet.sasinetTask.Repositry;

import com.sasinet.sasinetTask.entity.Account;
import com.sasinet.sasinetTask.entity.FixedDeposit;
import com.sasinet.sasinetTask.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FixedDepositeRepositry extends JpaRepository<FixedDeposit, Long> {
    List<FixedDeposit> findByAccount_User_Id(Long userID);
    FixedDeposit findByAccount(Account account);
}
