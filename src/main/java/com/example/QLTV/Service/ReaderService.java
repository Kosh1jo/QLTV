package com.example.QLTV.Service;

import com.example.QLTV.Dto.ReaderDto;

import java.util.List;

public interface ReaderService {

     String verifyToken(String token);

    void signUp(ReaderDto readerDto);

    List<ReaderDto> getAllReaders();

    boolean isEmailExists(String s);

    boolean editReaderById(int id, ReaderDto readerDto);

    ReaderDto getReaderById(int id);

    boolean isReaderIdExist(Integer id);

    String deleteReaderById(int id);
}
