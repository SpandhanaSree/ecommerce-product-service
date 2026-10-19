//Client <--- Backend
package com.trainingmug.ecommerce.productservice.dto.response;

import com.trainingmug.ecommerce.productservice.enums.Status;
import lombok.AllArgsConstructor; //Generates a constructor with parameters for all four fields.
import lombok.Builder; //Lets us create an object by setting fields with readable builder syntax.
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private String id;
    private String name;
    private int maxRetailPrice;
    private Status status;
}

/*Why are they different?
- The "request" DTO contains the information needed to submit a product.
- The "response" DTO contains the information selected for the client to receive.
- The "response" includes id, which is typically generated when the product is saved.
- The "response" omits fields such as company and discountPercentage. This can be an intentional design choice.
 */