
package com.trainingmug.ecommerce.productservice.dto.auth;

import lombok.Data;

@Data
public class SignupRequestDto {

    private String name;
    private String email;
    private String phone;
    private String password;
}
