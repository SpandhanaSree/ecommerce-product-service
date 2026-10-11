package com.trainingmug.ecommerce.productservice.controller;

import com.trainingmug.ecommerce.productservice.dto.auth.AuthResponseDto;
import com.trainingmug.ecommerce.productservice.dto.auth.LoginRequestDto;
import com.trainingmug.ecommerce.productservice.dto.auth.SignupRequestDto;
import com.trainingmug.ecommerce.productservice.dto.auth.UserResponseDto;
import com.trainingmug.ecommerce.productservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(
            @RequestBody SignupRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
