package com.sasinet.sasinetTask.Repositry;

import com.sasinet.sasinetTask.entity.Account;
import com.sasinet.sasinetTask.entity.Saving;
import com.sasinet.sasinetTask.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingRepositry extends JpaRepository<Saving, Long> {
    Saving findByAccount(Account account);
}
