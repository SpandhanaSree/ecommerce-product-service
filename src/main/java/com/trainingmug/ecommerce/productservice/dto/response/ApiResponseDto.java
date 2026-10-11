package com.trainingmug.ecommerce.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDto<T> {
    private boolean success;
    private String message;
    private int code;
    private T data;
    /*
    Void, Integer,String, List<String>,List<CustomerResponseDto>, Map
     */
}
