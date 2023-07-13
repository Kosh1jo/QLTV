package com.example.QLTV.Service.Impl;

import com.example.QLTV.Dto.LoanDto;
import com.example.QLTV.Dto.LoanRequest;
import com.example.QLTV.Entity.Loan;
import com.example.QLTV.Repository.BookRepository;
import com.example.QLTV.Repository.LoanRepository;
import com.example.QLTV.Repository.ReaderRepository;
import com.example.QLTV.Service.LoanService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReaderRepository readerRepository;
    @Autowired
    private LoanRepository loanRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);
    @Override
    @Transactional
    public Boolean createLoan(LoanRequest loanRequest) {
        try {
            Arrays.stream(loanRequest.getBookId()).forEach((id)->{
                Loan loan = new Loan();
                loan.setLoanDate(new Date());
                loan.setReader(readerRepository.getReaderById(loanRequest.getReaderId()));
                loan.setReturned(false);
                LocalDate localDate = LocalDate.now();
                try {
                    Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(localDate.plusDays(7).toString());
                    loan.setDueDate(dueDate);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                loan.setBook(bookRepository.getBookById(id));
                loanRepository.save(loan);
            });
            logger.info("Create Loan");
            return true;
        } catch (Exception e){
            logger.error("Create Loan fail");
            return false;
        }
    }

    @Override
    public List<LoanDto> getAllLoans() {
        List<Loan> loanList = loanRepository.findAll();
        List<LoanDto> loanDtoList = new ArrayList<>();
        for (Loan temp: loanList) {
            LoanDto loanDto = new LoanDto();
            loanDto.setId(temp.getId());
            loanDto.setReaderName(readerRepository.getReaderById(temp.getReader().getId()).getName());
            loanDto.setBookTitle(bookRepository.getBookById(temp.getBook().getId()).getTitle());
            loanDto.setLoanDate(String.valueOf(temp.getLoanDate()));
            loanDto.setDueDate(String.valueOf(temp.getDueDate()));
            loanDto.setReturned(String.valueOf(temp.isReturned()));
            loanDtoList.add(loanDto);
        }
        logger.info("Get All Loans");
        return loanDtoList;
    }

    @Override
    public LoanDto getLoanById(int id) {
        Loan loan = loanRepository.getLoanById(id);
        LoanDto Dto = new LoanDto();
        Dto.setId(loan.getId());
        Dto.setReaderName(readerRepository.getReaderById(loan.getReader().getId()).getName());
        Dto.setBookTitle(bookRepository.getBookById(loan.getBook().getId()).getTitle());
        Dto.setLoanDate(String.valueOf(loan.getLoanDate()));
        Dto.setDueDate(String.valueOf(loan.getDueDate()));
        Dto.setReturned(String.valueOf(loan.isReturned()));
        logger.info("Get Loan" + id);
        return Dto;
    }

    @Override
    public boolean isLoanExistsByID(Integer id) {
        return loanRepository.existsLoanById(id);
    }

    @Override
    @Transactional
    public boolean editLoanById(int id, LoanDto loanDto) {
        try {
            Loan loan = loanRepository.getLoanById(id);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(loanDto.getDueDate());
                loan.setDueDate(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            loan.setReturned(Boolean.parseBoolean(loanDto.getReturned()));
            loanRepository.save(loan);
            logger.info("edit loan by Id " + id + " complete");
            return true;
        }catch (Exception e){
            logger.error("edit loan fail");
            return false;
        }
    }

    @Override
    public String deleteLoanById(int id) {
        loanRepository.deleteById(id);
        logger.info("Delete loan complete");
        return "Delete Success";
    }
}
