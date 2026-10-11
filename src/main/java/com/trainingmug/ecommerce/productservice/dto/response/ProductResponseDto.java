package com.trainingmug.ecommerce.productservice.dto.response;

import com.trainingmug.ecommerce.productservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
