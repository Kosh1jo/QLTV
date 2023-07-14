package com.example.QLTV.Controller;

import com.example.QLTV.Dto.BaseResponse;
import com.example.QLTV.Dto.LoanDto;
import com.example.QLTV.Dto.LoanRequest;
import com.example.QLTV.Dto.ReaderDto;
import com.example.QLTV.Service.LoanService;
import com.example.QLTV.Validation.LoanIdExists;
import com.example.QLTV.Validation.ReaderIdExists;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/loans")
@Validated
public class LoanController {
    @Autowired
    LoanService loanService;

    @PostMapping("/create")
    public ResponseEntity<?> createLoan(@RequestBody LoanRequest loanRequest){
        if(loanService.createLoan(loanRequest)){
            ResponseEntity.ok("Create Loan complete");
        }
        return ResponseEntity.badRequest().body("Create Loan fail");
    }
    @GetMapping("/reader/{id}")
    public ResponseEntity<?> getLoanReaderById(@PathVariable(name = "id") @ReaderIdExists int id){
        List<LoanDto> Dto = loanService.getLoanReaderById(id);
        return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(), Dto,"Get Loan id = " + id + " complete"));
    }
    @GetMapping
    public ResponseEntity<BaseResponse> getAllLoans(){
        List<LoanDto> LoanDtoList = loanService.getAllLoans();
        if(LoanDtoList.isEmpty()){
            return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(), "","Not valid data"));
        }
        return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(),LoanDtoList,"Get all Loans complete"));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getLoanById(@PathVariable(name = "id") @LoanIdExists int id){
        LoanDto Dto = loanService.getLoanById(id);
        return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(), Dto,"Get Loan id = " + id + " complete"));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editLoanById(@PathVariable(name = "id") @LoanIdExists int Id, @RequestBody LoanDto loanDto){
        if(loanService.editLoanById(Id,loanDto)){
            return ResponseEntity.ok("Edit Reader id = " + Id + " complete");
        }else {
            return ResponseEntity.badRequest().body("Edit Reader fail");
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReaderById(@PathVariable(name = "id") @LoanIdExists int Id){
        String result = loanService.deleteLoanById(Id);
        return ResponseEntity.ok(result);
    }
}
