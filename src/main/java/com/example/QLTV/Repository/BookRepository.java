package com.example.QLTV.Repository;

import com.example.QLTV.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Book getBookById(int id);
    boolean existsBookById(int id);
}
