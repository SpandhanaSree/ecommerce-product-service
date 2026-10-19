package com.trainingmug.ecommerce.productservice.exception;

import com.trainingmug.ecommerce.productservice.dto.response.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice; //enables centralized exception handling for REST controllers

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductExistsException.class)
    public ResponseEntity<ApiResponseDto<String>> handleProductExistsException(ProductExistsException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body( //Sets HTTP status 409
                ApiResponseDto.<String>builder()
                        .success(false)
                        .message(e.getMessage())
                        .code(HttpStatus.CONFLICT.value())
                        .data(e.getMessage())
                        .build()
        );
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponseDto<String>> handleProductNotFoundException(ProductNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( //Sets HTTP status 404
                ApiResponseDto.<String>builder()
                        .success(false)
                        .message(e.getMessage())
                        .code(HttpStatus.NOT_FOUND.value())
                        .data(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<String>> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( //Sets HTTP status 500
                ApiResponseDto.<String>builder()
                        .success(false)
                        .message(e.getMessage())
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .data(e.getMessage())
                        .build());
    }
}
