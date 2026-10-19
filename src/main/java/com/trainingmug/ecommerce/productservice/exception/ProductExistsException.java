package com.trainingmug.ecommerce.productservice.exception;

    /*  This creates a custom exception for situations where a product already exists.For example, 
    suppose our application does not allow duplicate product names. If someone tries to add a product that already exists,
    the service can throw ProductExistsException.Instead of showing a generic error, 
    we can provide a meaningful message such as "Product already exists". */
    
public class ProductExistsException extends RuntimeException {
    public ProductExistsException(String message) {
        super(message);
    }
}