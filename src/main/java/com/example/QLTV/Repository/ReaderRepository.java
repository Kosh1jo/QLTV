package com.example.QLTV.Repository;

import com.example.QLTV.Entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader,Integer> {
    Optional<Reader> findReadersByEmailAndActive(String s, boolean active);
    boolean existsReaderByIdAndActive(int id,boolean active);
    Reader getReaderById(int Id);
}
