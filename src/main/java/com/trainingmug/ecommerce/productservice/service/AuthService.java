
package com.trainingmug.ecommerce.productservice.service;

import com.trainingmug.ecommerce.productservice.dto.auth.AuthResponseDto;
import com.trainingmug.ecommerce.productservice.dto.auth.LoginRequestDto;
import com.trainingmug.ecommerce.productservice.dto.auth.SignupRequestDto;
import com.trainingmug.ecommerce.productservice.dto.auth.UserResponseDto;

public interface AuthService {

    UserResponseDto signup(SignupRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
}
