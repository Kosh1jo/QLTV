package com.example.QLTV.Controller;

import com.example.QLTV.Dto.AuthResponseDTO;
import com.example.QLTV.Dto.BaseResponse;
import com.example.QLTV.Dto.LoginDto;
import com.example.QLTV.Dto.ReaderDto;
import com.example.QLTV.Security.JwtProvider;
import com.example.QLTV.Service.ReaderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private ReaderService readerService;
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid ReaderDto readerDto){
        readerService.signUp(readerDto);
        return ResponseEntity.ok("Please check your Email");
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
    @GetMapping("/verify/{token}")
    public ResponseEntity<BaseResponse> verifyToken(@PathVariable(value = "token") String token){
        String activeMsg= readerService.verifyToken(token);
        return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(),null,activeMsg));
    }
}
