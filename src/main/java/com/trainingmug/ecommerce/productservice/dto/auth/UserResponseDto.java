package com.trainingmug.ecommerce.productservice.dto.auth;

import lombok.Data;

@Data
public class UserResponseDto {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String role;
}