package com.example.QLTV.Repository;

import com.example.QLTV.Entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    Loan getLoanById(int id);

    boolean existsLoanById(Integer id);
}