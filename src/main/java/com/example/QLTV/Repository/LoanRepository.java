package com.example.QLTV.Repository;

import com.example.QLTV.Entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    Loan getLoanById(int id);

    boolean existsLoanById(Integer id);
    List<Loan> findLoansByReaderId(int id);
}