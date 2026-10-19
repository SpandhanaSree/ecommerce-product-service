package com.trainingmug.ecommerce.productservice.service;

import com.trainingmug.ecommerce.productservice.dto.request.ProductRequestDto; //carries product details coming into our application.
import com.trainingmug.ecommerce.productservice.dto.response.ProductResponseDto; //carries product details going back to the client
import com.trainingmug.ecommerce.productservice.exception.ProductExistsException; //can be used when a product already exists.
import com.trainingmug.ecommerce.productservice.exception.ProductNotFoundException; //can be used when a requested product cannot be found

import java.util.List; //represent multiple products in an ordered collection

//An interface defines what operations must be available, without specifying how those operations work
public interface ProductService {

    //1. Save a product
    ProductResponseDto save(ProductRequestDto requestDto) throws ProductExistsException;

    //2. Get one product by ID
    ProductResponseDto getById(String id) throws ProductNotFoundException;

    //3. Get all products
    //return type is List<ProductResponseDto>, not just ProductResponseDto, because multiple products can be returned
    List<ProductResponseDto> getAll();

    //4. Update a product
    //This updates a product identified by id, using the new details in requestDto    
    ProductResponseDto update(String id, ProductRequestDto requestDto) throws ProductNotFoundException;

    //5. Delete a product
    //void - Doesn't return any value.
    void delete(String id) throws ProductNotFoundException;

}
