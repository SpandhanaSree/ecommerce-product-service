
package com.trainingmug.ecommerce.productservice.service;
/*
    Request DTO: client → backend
    Response DTO: backend → client 
*/

import com.trainingmug.ecommerce.productservice.dto.auth.AuthResponseDto;
import com.trainingmug.ecommerce.productservice.dto.auth.LoginRequestDto;
import com.trainingmug.ecommerce.productservice.dto.auth.SignupRequestDto;
import com.trainingmug.ecommerce.productservice.dto.auth.UserResponseDto;

public interface AuthService {

    UserResponseDto signup(SignupRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
}
