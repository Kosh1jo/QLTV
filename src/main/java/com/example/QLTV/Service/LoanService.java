package com.example.QLTV.Service;

import com.example.QLTV.Dto.LoanDto;
import com.example.QLTV.Dto.LoanRequest;

import java.util.List;

public interface LoanService {
    Boolean createLoan(LoanRequest loanRequest);

    List<LoanDto> getAllLoans();

    LoanDto getLoanById(int id);

    boolean isLoanExistsByID(Integer id);

    boolean editLoanById(int id, LoanDto loanDto);

    String deleteLoanById(int id);

    List<LoanDto> getLoanReaderById(int id);
}
