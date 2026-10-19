/*DTO = Data Transfer Object.
Application lo oka layer nunchi vere layer ki, especially 
client ki data pampadaniki or client nunchi data receive cheyadaniki use chestham
*/
package com.trainingmug.ecommerce.productservice.dto.auth;

import lombok.Data;

@Data
public class AuthResponseDto {

    private String accessToken;
    private String refreshToken;
    private UserResponseDto user;
}
