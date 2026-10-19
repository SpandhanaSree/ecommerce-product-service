package com.trainingmug.ecommerce.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

/*Declares a public class with a generic type parameter T

/T is a placeholder for a data type. When you use the class, you choose what T represents.

 Ex:- ApiResponseDto<ProductResponseDto> // Here, T represents ProductResponseDto, so the data field holds one product response.
ApiResponseDto<List<ProductResponseDto>> // Here, T represents a list of product responses, so data can hold multiple products.

This is called generics in Java. It lets one class work with different data types without 
creating a separate response class for every type*/

public class ApiResponseDto<T> { 
    private boolean success;
    private String message;
    private int code;
    private T data;
}
