package com.trainingmug.ecommerce.productservice.service.impl;

import com.trainingmug.ecommerce.productservice.document.Product;
import com.trainingmug.ecommerce.productservice.dto.request.ProductRequestDto;
import com.trainingmug.ecommerce.productservice.dto.response.ProductResponseDto;
import com.trainingmug.ecommerce.productservice.enums.Status;
import com.trainingmug.ecommerce.productservice.exception.ProductExistsException;
import com.trainingmug.ecommerce.productservice.exception.ProductNotFoundException;
import com.trainingmug.ecommerce.productservice.repository.ProductRepository; //Communicates with MongoDB through Spring Data
import com.trainingmug.ecommerce.productservice.service.ProductService; //The interface this class implements

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper; //Converts between DTO objects and Product objects
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service //tells Spring this class contains service-layer logic, so Spring can manage it as a bean
@RequiredArgsConstructor //generates a constructor with required arguments (final fields), so we don't have to write it manually
public class ProductServiceImpl implements ProductService {

    /*Because these fields are final, they must be initialized through a constructor. 
    Lombok generates that constructor, and Spring injects the required objects.
    This is called constructor dependency injection. */

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper; //modelMapper converts between ProductRequestDto, Product, and ProductResponseDto

    @Override
    public ProductResponseDto save(ProductRequestDto requestDto) throws ProductExistsException {

        //1. Check if a product with the same name already exists in the database
        productRepository.findByName(requestDto.getName()).ifPresent(product -> {
            throw new ProductExistsException("Product already exists with the name: " + requestDto.getName() );
        });

        //2. Convert the request DTO into a product
        Product product = modelMapper.map(requestDto, Product.class);
        product.setStatus(Status.AVAILABLE);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        /*  productRepository.save(product) ---- saves the product to MongoDB and returns the saved object.
            modelMapper.map(..., ProductResponseDto.class) ---- converts that saved object into the response DTO. */
        return modelMapper.map(productRepository.save(product), ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto getById(String id) throws ProductNotFoundException {
        return modelMapper.map(
            productRepository.findById(id)
            .orElseThrow(() -> 
            new ProductNotFoundException(
                "Product not found with the id: " + id
            )), ProductResponseDto.class
        );
    }

    @Override
    public List<ProductResponseDto> getAll() { //getAll() -- No parameters are needed because we aren't searching for one specific product
        return productRepository.findAll().stream() //stream()-- Converts the list into a Java Stream so we can process its elements one by one
            .map(product ->
             /* The outer .map() processes each product in the stream.
                For every product, the inner modelMapper.map() converts the Product object into a ProductResponseDto
             */
                modelMapper.map(product, ProductResponseDto.class)
        )    
        .toList();
    }

    @Override
    public ProductResponseDto update(String id, ProductRequestDto requestDto) throws ProductNotFoundException {
        productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with the id: " + id));
        return modelMapper.map(productRepository.save(modelMapper.map(requestDto, Product.class)),
          ProductResponseDto.class
        );
    }

    @Override
    public void delete(String id) throws ProductNotFoundException {
        productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with the id: " + id));
        productRepository.deleteById(id);
    }
}
