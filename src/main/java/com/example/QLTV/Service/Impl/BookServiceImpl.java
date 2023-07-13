package com.example.QLTV.Service.Impl;

import com.example.QLTV.Convert.BookConvert;
import com.example.QLTV.Dto.BookDto;
import com.example.QLTV.Entity.Book;
import com.example.QLTV.Repository.BookRepository;
import com.example.QLTV.Service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookConvert bookConvert;
    @Override
    public Boolean createBook(BookDto bookDto) {
        try {
            Book book = bookConvert.toEntity(bookDto);
            bookRepository.save(book);
            logger.info("Create complete");
            return true;
        }
        catch (Exception ignored){
        }
        logger.error("Create Error");
        return false;
    }

    @Override
    public List<BookDto> getAll() {
        List<Book> bookList = bookRepository.findAll();
        List<BookDto> bookDtoList=new ArrayList<>();
        for (Book book:bookList) {
            BookDto bookDto = bookConvert.toDTO(book);
            bookDtoList.add(bookDto);
        }
        logger.info("Get all book complete");
        return bookDtoList;
    }

    @Override
    public BookDto getBookById(int id) {
        Book book = bookRepository.getBookById(id);
        logger.info("Get all books complete");
        return bookConvert.toDTO(book);
    }

    @Override
    public boolean isBookExistsByID(int id) {
        return bookRepository.existsBookById(id);
    }

    @Override
    public boolean editBookById(int id,BookDto bookDto) {
        try {
            Book book = bookRepository.getBookById(id);
            book.setTitle(bookDto.getTitle());
            book.setAuthor(bookDto.getAuthor());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(bookDto.getPublicationYear());
                book.setPublicationYear(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            bookRepository.save(book);
            logger.info("Edit book complete");
            return true;
        }catch (Exception exception){
            return false;
        }

    }

    @Override
    public String deleteBookById(int id) {
        bookRepository.deleteById(id);
        logger.info("Delete book complete");
        return "Delete Success";
    }
}
