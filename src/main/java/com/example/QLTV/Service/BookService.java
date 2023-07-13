package com.example.QLTV.Service;

import com.example.QLTV.Dto.BookDto;

import java.util.List;

public interface BookService {
    Boolean createBook(BookDto bookDto);

    List<BookDto> getAll();
    BookDto getBookById(int id);

    boolean isBookExistsByID(int id);

    boolean editBookById(int id,BookDto bookDto);

    String deleteBookById(int id);
}
