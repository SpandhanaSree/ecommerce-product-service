//Backend <--->   MongoDB
package com.trainingmug.ecommerce.productservice.document;

import com.trainingmug.ecommerce.productservice.enums.Category;
import com.trainingmug.ecommerce.productservice.enums.Status;
import lombok.Data; //Lombok annotation that generates common methods
import lombok.NoArgsConstructor; //Lombok annotation that generates a no-argument constructor
import org.springframework.data.annotation.Id; //Marks a field in a class as the primary key for a document in a MongoDB collection
import org.springframework.data.mongodb.core.mapping.Document; //Maps this class to a MongoDB collection
import java.time.LocalDateTime; //Represents date and time without a time-zone offset

@Data //Lombok annotation that generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor //Lombok annotation that generates a no-argument constructor -- Product product = new Product();
@Document(collection = "products") //Maps this class to a MongoDB collection named "products"

public class Product {
    @Id //Identifies the product
    private String id;
    private String name;
    private int maxRetailPrice;
    private float discountPercentage;
    private Category category; //Use enums -- private restricts direct field access from outside the class
    private String company; 
    private Status status; //Use enums -- private restricts direct field access from outside the class
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}