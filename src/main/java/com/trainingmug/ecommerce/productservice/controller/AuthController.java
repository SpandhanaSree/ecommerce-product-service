package com.trainingmug.ecommerce.productservice.controller;

import com.trainingmug.ecommerce.productservice.dto.auth.AuthResponseDto;
import com.trainingmug.ecommerce.productservice.dto.auth.LoginRequestDto;
import com.trainingmug.ecommerce.productservice.dto.auth.SignupRequestDto;
import com.trainingmug.ecommerce.productservice.dto.auth.UserResponseDto;
import com.trainingmug.ecommerce.productservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //tells Spring this class handles web requests and returns response data, usually as JSON
@RequestMapping("/api/auth") //defines the common URL prefix for this controller
public class AuthController {

    //private final means the field can be assigned once, through the constructor
    //AuthService authService declares a variable for accessing the authentication service
    private final AuthService authService; 
    /*
        The constructor receives an AuthService object.
        this.authService = authService; stores that object in the controller.
    */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(

            //@RequestBody -- tells Spring to convert the incoming JSON into a SignupRequestDto object
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
