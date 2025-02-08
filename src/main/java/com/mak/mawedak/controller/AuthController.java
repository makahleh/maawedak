package com.mak.mawedak.controller;

import com.mak.mawedak.dto.AuthResponseDTO;
import com.mak.mawedak.dto.LoginDTO;
import com.mak.mawedak.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto) {
        AuthResponseDTO authResponseDto = authService.login(loginDto);
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }
}
