package com.sasinet.sasinetTask.Repositry;

import com.sasinet.sasinetTask.entity.Account;
import com.sasinet.sasinetTask.entity.Loan;
import com.sasinet.sasinetTask.entity.Saving;
import com.sasinet.sasinetTask.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SavingRepositry extends JpaRepository<Saving, Long> {
    Saving findByAccount(Account account);
    List<Saving> findByAccount_User_Id(Long id);

    @Query("SELECT s.account.id FROM Saving s WHERE s.account.user.id = :userId")
    List<Long> findAccountIdsByUserId(Long userId);
}
