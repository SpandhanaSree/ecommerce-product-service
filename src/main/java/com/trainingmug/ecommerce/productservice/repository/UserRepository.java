package com.trainingmug.ecommerce.productservice.repository;

import com.trainingmug.ecommerce.productservice.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
