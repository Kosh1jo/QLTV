package com.example.QLTV.Service.Impl;

import com.example.QLTV.Convert.ReaderConvert;
import com.example.QLTV.Dto.ReaderDto;
import com.example.QLTV.Entity.Reader;
import com.example.QLTV.Entity.Role;
import com.example.QLTV.Entity.SignUpToken;
import com.example.QLTV.Repository.ReaderRepository;
import com.example.QLTV.Repository.TokenRepository;
import com.example.QLTV.Service.ReaderService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReaderServiceImpl implements ReaderService {
    private static final Logger logger = LoggerFactory.getLogger(ReaderServiceImpl.class);
    @Autowired
    ReaderConvert readerConvert;
    @Autowired
    ReaderRepository readerRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String verifyToken(String token) {
        Optional<SignUpToken> optionalSignUpToken = tokenRepository.findSignUpTokenByToken(token);
        if (optionalSignUpToken.isEmpty() ) {
            return "Activate failed";
        }
        SignUpToken signUpToken = optionalSignUpToken.get();
        Date expirationTime = new Date(signUpToken.getExpirationTime().getTime()+(15*60*1000));
        Reader reader = signUpToken.getReader();
        if(signUpToken.getExpirationTime().after(expirationTime)){
            return "Activate failed";
        }else {
            reader.setActive(true);
            readerRepository.save(reader);
            return "Activate successfully";
        }
    }

    @Override
    @Transactional
    public void signUp(ReaderDto readerDto) {
        Reader reader = readerConvert.toEntity(readerDto);
        reader.setActive(false);
        reader.setRoles(Role.USER);
        reader.setPassword(passwordEncoder.encode(readerDto.getPassword()));
        Reader readerSave = readerRepository.save(reader);
        generateToken(reader);
    }

    @Override
    public List<ReaderDto> getAllReaders() {
        logger.info("Get all reader");
        return new ArrayList<>(readerRepository.findAll()
                .stream()
                .filter(reader -> reader.getRoles().equals(Role.USER))
                .filter(Reader::isActive)
                .map(readerConvert::toDTO).toList());
    }

    @Override
    public boolean isEmailExists(String s) {
        Optional<Reader> reader = readerRepository.findReaderByEmailAndActive(s, true);
        return (long) reader.stream().toList().size() > 1;
    }

    @Override
    public boolean editReaderById(int id, ReaderDto readerDto) {
        try {
            Reader reader = readerRepository.getReaderById(id);
            reader.setPassword(readerDto.getPassword());
            reader.setName(readerDto.getName());
            reader.setEmail(readerDto.getEmail());
            reader.setPhone(readerDto.getPhone());
            readerRepository.save(reader);
            logger.info("edit reader by Id " + id + " complete");
            return true;
        }catch (Exception e){
            logger.error("edit reader fail");
            return false;
        }
    }

    @Override
    public ReaderDto getReaderById(int id) {
        Reader reader = readerRepository.getReaderById(id);
        logger.info("Get reader by id " + id);
        return readerConvert.toDTO(reader);
    }

    @Override
    public boolean isReaderIdExist(Integer id) {
        return readerRepository.existsReaderByIdAndActive(id,true);
    }

    @Override
    public String deleteReaderById(int id) {
        readerRepository.deleteById(id);
        logger.info("Delete reader complete");
        return "Delete Success";
    }

    public void generateToken(Reader reader){
        UUID token = UUID.randomUUID();
        SignUpToken signUpToken = new SignUpToken();
        signUpToken.setReader(reader);
        signUpToken.setToken(String.valueOf(token));
        signUpToken.setExpirationTime(new Date());
        tokenRepository.save(signUpToken);
        try {
            emailService.sendEmail(reader.getEmail(), "Please active your account", "localhost:8090/api/auth/verify/" + token);
        } catch (Exception e) {
            logger.error("Send email failed");
            throw e;
        }
        logger.info("Send email activate successfully");
    }
}
