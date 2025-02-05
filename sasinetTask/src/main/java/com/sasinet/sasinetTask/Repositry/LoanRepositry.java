package com.sasinet.sasinetTask.Repositry;

import com.sasinet.sasinetTask.entity.Account;
import com.sasinet.sasinetTask.entity.Loan;
import com.sasinet.sasinetTask.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepositry extends JpaRepository<Loan, Long> {
    Loan findByAccount(Account account);
    List<Loan> findByAccount_User_Id(Long id);
}
