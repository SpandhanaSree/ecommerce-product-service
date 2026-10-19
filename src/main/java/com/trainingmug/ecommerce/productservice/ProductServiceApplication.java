package com.trainingmug.ecommerce.productservice;

import org.springframework.boot.SpringApplication; //This is a class that helps launch our Spring Boot application
import org.springframework.boot.autoconfigure.SpringBootApplication; //This is an annotation that helps Spring Boot auto-arrange our application based on the dependencies we have added in the pom.xml file.

@SpringBootApplication //@SpringBootApplication = ee class Spring Boot application ki main setup class ani indicate chesthundhi
public class ProductServiceApplication {

    public static void main(String[] args) {

        //SpringApplication.run() = application ni start cheyyadaniki use chese call
        SpringApplication.run(ProductServiceApplication.class, args);
            //ProductServiceApplication.class = Which class should Spring Boot start from?
            //args = What command-line arguments were passed when the application started?
    }

}
