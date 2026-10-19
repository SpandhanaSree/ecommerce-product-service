package com.trainingmug.ecommerce.productservice.controller;

import com.trainingmug.ecommerce.productservice.dto.request.ProductRequestDto;
import com.trainingmug.ecommerce.productservice.dto.response.ApiResponseDto; //Provides a common structure for API responses
import com.trainingmug.ecommerce.productservice.dto.response.ProductResponseDto;
import com.trainingmug.ecommerce.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus; //Provides HTTP status codes, such as 200 and 201
import org.springframework.http.ResponseEntity; //Lets us control the HTTP response and status
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Tells Spring that this class handles web requests and returns data in the response body, usually as JSON
@RequestMapping("/api/products")
@RequiredArgsConstructor //generates a constructor for required fields, including our final ProductService field
public class ProductController {

    private final ProductService productService;

    @PostMapping
    //@RequestBody - tells Spring to convert the incoming JSON request body into a ProductRequestDto object
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> save(@RequestBody ProductRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body( //Sets the HTTP status code to 201 Created and returns the response body
                ApiResponseDto.<ProductResponseDto>builder() //Creates a new ApiResponseDto object using the builder pattern
                        .success(true)
                        .message("Product created successfully")
                        .code(HttpStatus.CREATED.value()) //Sets the HTTP status code 201
                        .data(productService.save(requestDto)) //Calls the ProductService to save the product and returns the saved product data in the response
                        .build()
        );
    }
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getAll(){
        return ResponseEntity.ok( 
                ApiResponseDto.<List<ProductResponseDto>>builder()
                        .success(true)
                        .message("Products fetched successfully")
                        .code(HttpStatus.OK.value()) //Sets the HTTP status code 200
                        .data(productService.getAll())
                        .build()
        );

    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> getById(@PathVariable String id){
        return ResponseEntity.ok(
                ApiResponseDto.<ProductResponseDto>builder()
                        .success(true)
                        .message("Product fetched successfully")
                        .code(HttpStatus.OK.value()) //Sets the HTTP status code 200
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
                        .code(HttpStatus.OK.value()) //Sets the HTTP status code 200
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
                        .code(HttpStatus.OK.value()) //Sets the HTTP status code 200
                        .build()
        );
    }
}
