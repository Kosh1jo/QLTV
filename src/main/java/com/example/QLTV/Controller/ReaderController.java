package com.example.QLTV.Controller;

import com.example.QLTV.Dto.BaseResponse;
import com.example.QLTV.Dto.ReaderDto;
import com.example.QLTV.Service.ReaderService;

import com.example.QLTV.Validation.BookIdExists;
import com.example.QLTV.Validation.ReaderIdExists;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/readers")
@Validated
public class ReaderController {
    @Autowired
    private ReaderService readerService;
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid ReaderDto readerDto){
        readerService.signUp(readerDto);
        return ResponseEntity.ok("Please check your Email");
    }
    @GetMapping("/verify/{token}")
    public ResponseEntity<BaseResponse> verifyToken(@PathVariable(value = "token") String token){
        String activeMsg= readerService.verifyToken(token);
        return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(),null,activeMsg));
    }
    @GetMapping
    public ResponseEntity<BaseResponse> getAllReaders(){
        List<ReaderDto> readerDtoList = readerService.getAllReaders();
        if(readerDtoList.isEmpty()){
            return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(), "","Not valid data"));
        }
        return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(),readerDtoList,"Get all readers complete"));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getReaderId(@PathVariable(name = "id") @ReaderIdExists int Id){
        ReaderDto Dto = readerService.getReaderById(Id);
        return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(), Dto,"Get Reader id = " + Id + " complete"));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editReaderById(@PathVariable(name = "id") @ReaderIdExists int Id, @RequestBody @Valid ReaderDto readerDto){
        if(readerService.editReaderById(Id,readerDto)){
            return ResponseEntity.ok("Edit Reader id = " + Id + " complete");
        }else {
            return ResponseEntity.badRequest().body("Edit Reader fail");
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReaderById(@PathVariable(name = "id") @ReaderIdExists int Id){
        String result = readerService.deleteReaderById(Id);
        return ResponseEntity.ok(result);
    }
}
