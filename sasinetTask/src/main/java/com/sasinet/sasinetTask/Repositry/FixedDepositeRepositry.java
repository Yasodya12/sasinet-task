package com.sasinet.sasinetTask.Repositry;

import com.sasinet.sasinetTask.entity.Account;
import com.sasinet.sasinetTask.entity.FixedDeposit;
import com.sasinet.sasinetTask.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedDepositeRepositry extends JpaRepository<FixedDeposit, Long> {
    FixedDeposit findByAccount(Account account);
}
