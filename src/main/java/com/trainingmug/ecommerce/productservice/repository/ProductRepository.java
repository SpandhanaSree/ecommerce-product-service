package com.trainingmug.ecommerce.productservice.repository;

import com.trainingmug.ecommerce.productservice.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository; 
    /*repository.MongoRepository - provides ready-made database operations, including:
            Method	            Purpose
        save(product)	-    Insert or update a product
        findAll()	    -   Fetch all products
        findById(id)	-   Find a product by ID
        deleteById(id)	-   Delete a product by ID
        existsById(id)	-   Check whether a product exists 
    */

import java.util.Optional; //Optional represents a value that might exist or might be absent, helps us handle missing products safely.

public interface ProductRepository extends MongoRepository<Product,String> { 
    /*  extends MongoRepository — inherits the ready-made MongoDB operations.
        Product                 — the document type this repository manages.
        String                  — the data type of the document's ID.
     */
    Optional<Product> findByName(String name); 
    //Spring Data MongoDB recognizes the method name findByName and derives a query from it
}
