package com.example.QLTV.Convert;

import com.example.QLTV.Dto.BookDto;
import com.example.QLTV.Entity.Book;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class BookConvert {
    public BookDto toDTO(Book book){
        BookDto result = new BookDto();
        if(book.getId() != 0){
            result.setId(book.getId());
        }
        result.setTitle(book.getTitle());
        result.setAuthor(book.getAuthor());
        result.setPublicationYear(String.valueOf(book.getPublicationYear()));
        return result;
    }
    public Book toEntity(BookDto bookDto) throws ParseException {
        Book result= new Book();
        result.setTitle(bookDto.getTitle());
        result.setAuthor(bookDto.getAuthor());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(bookDto.getPublicationYear());
        result.setPublicationYear(date);
        return result;
    }
}
