package com.example.QLTV.Repository;

import com.example.QLTV.Entity.SignUpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<SignUpToken,Integer> {
    Optional<SignUpToken> findSignUpTokenByToken(String token);
}
