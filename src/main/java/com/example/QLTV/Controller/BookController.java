package com.example.QLTV.Controller;

import com.example.QLTV.Dto.BaseResponse;
import com.example.QLTV.Dto.BookDto;
import com.example.QLTV.Service.BookService;
import com.example.QLTV.Validation.BookIdExists;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
@Validated
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<?> createBook(@RequestBody @Valid BookDto bookDto){
        if(bookService.createBook(bookDto)){
            return ResponseEntity.ok("Create Book complete!");
        } else {
            return ResponseEntity.badRequest().body("Create Book error");
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllBooks(){
        List<BookDto> list = bookService.getAll();
        if(!list.isEmpty()){
            return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(), list,"Get All books complete"));
        }
        return ResponseEntity.badRequest().body("Get books fail");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable(name = "id") @BookIdExists int Id){
        BookDto bookDto = bookService.getBookById(Id);
        return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(), bookDto,"Get book id = " + Id + " complete"));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editBookById(@PathVariable(name = "id") @BookIdExists int Id,@RequestBody @Valid BookDto bookDto){
        if(bookService.editBookById(Id,bookDto)){
            return ResponseEntity.ok("Edit book id = " + Id + " complete");
        }
        return ResponseEntity.badRequest().body("Edit book fail");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable(name = "id") @BookIdExists int Id){
        String result = bookService.deleteBookById(Id);
        return ResponseEntity.ok(result);
    }
}
