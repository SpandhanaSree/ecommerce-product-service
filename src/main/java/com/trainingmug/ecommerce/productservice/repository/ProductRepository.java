package com.trainingmug.ecommerce.productservice.repository;

import com.trainingmug.ecommerce.productservice.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product,String> {

    Optional<Product> findByName(String name);

}
