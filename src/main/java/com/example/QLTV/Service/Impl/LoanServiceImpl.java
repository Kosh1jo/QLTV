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
            LoanDto loanDto = toDTO(temp);
            loanDtoList.add(loanDto);
        }
        logger.info("Get All Loans");
        return loanDtoList;
    }

    @Override
    public LoanDto getLoanById(int id) {
        Loan loan = loanRepository.getLoanById(id);
        return toDTO(loan);
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

    @Override
    public List<LoanDto> getLoanReaderById(int id) {
        List<Loan> loans = loanRepository.findLoansByReaderId(id);
        List<LoanDto> list = new ArrayList<>();
        loans.forEach(loan -> {
            LoanDto dto = toDTO(loan);
            list.add(dto);
        });

        logger.info("Get Loans reader by Id " + id);
        return list;
    }
    private LoanDto toDTO(Loan loan){
        LoanDto temp = new LoanDto();
        temp.setId(loan.getId());
        temp.setReaderName(readerRepository.getReaderById(loan.getReader().getId()).getName());
        temp.setBookTitle(bookRepository.getBookById(loan.getBook().getId()).getTitle());
        temp.setLoanDate(String.valueOf(loan.getLoanDate()));
        temp.setDueDate(String.valueOf(loan.getDueDate()));
        temp.setReturned(String.valueOf(loan.isReturned()));
        return temp;
    }
}
