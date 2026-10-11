
package com.trainingmug.ecommerce.productservice.dto.auth;

import lombok.Data;

@Data
public class AuthResponseDto {

    private String accessToken;
    private String refreshToken;
    private UserResponseDto user;
}
