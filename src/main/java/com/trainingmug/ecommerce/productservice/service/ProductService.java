package com.trainingmug.ecommerce.productservice.service;

import com.trainingmug.ecommerce.productservice.dto.request.ProductRequestDto;
import com.trainingmug.ecommerce.productservice.dto.response.ProductResponseDto;
import com.trainingmug.ecommerce.productservice.exception.ProductExistsException;
import com.trainingmug.ecommerce.productservice.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {

    ProductResponseDto save(ProductRequestDto requestDto) throws ProductExistsException;
    ProductResponseDto getById(String id) throws ProductNotFoundException;
    List<ProductResponseDto> getAll();
    ProductResponseDto update(String id, ProductRequestDto requestDto) throws ProductNotFoundException;
    void delete(String id) throws ProductNotFoundException;

}
