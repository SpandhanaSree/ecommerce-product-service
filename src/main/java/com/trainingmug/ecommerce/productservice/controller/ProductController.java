package com.trainingmug.ecommerce.productservice.controller;

import com.trainingmug.ecommerce.productservice.dto.request.ProductRequestDto;
import com.trainingmug.ecommerce.productservice.dto.response.ApiResponseDto;
import com.trainingmug.ecommerce.productservice.dto.response.ProductResponseDto;
import com.trainingmug.ecommerce.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> save(@RequestBody ProductRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDto.<ProductResponseDto>builder()
                        .success(true)
                        .message("Product created successfully")
                        .code(HttpStatus.CREATED.value())
                        .data(productService.save(requestDto))
                        .build()
        );
    }
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getAll(){
        return ResponseEntity.ok(
                ApiResponseDto.<List<ProductResponseDto>>builder()
                        .success(true)
                        .message("Products fetched successfully")
                        .code(HttpStatus.OK.value())
                        .data(productService.getAll())
                        .build()
        );

    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> getBydId(@PathVariable String id){
        return ResponseEntity.ok(
                ApiResponseDto.<ProductResponseDto>builder()
                        .success(true)
                        .message("Product fetched successfully")
                        .code(HttpStatus.OK.value())
                        .data(productService.getById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> update(@PathVariable String id, @RequestBody ProductRequestDto productRequestDto){
        return ResponseEntity.ok(
                ApiResponseDto.<ProductResponseDto>builder()
                        .success(true)
                        .message("Product updated successfully")
                        .code(HttpStatus.OK.value())
                        .data(productService.update(id, productRequestDto))
                        .build()
        );


    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteById(@PathVariable String id){
        return ResponseEntity.ok(
                ApiResponseDto.<Void>builder()
                        .success(true)
                        .message("Product deleted successfully")
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }
}
