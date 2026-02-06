package org.abrar.storemanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 20, message = "Product name must be between 2 and 20 characters")
    private String productName;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true, message = "Selling price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Selling price can have up to 2 decimal places")
    private Double sellingPrice;

    @NotNull
    @Min(value = 0, message = "Tax cannot be negative")
    private Integer tax;


    private Long version;

    private List<ProductVariantDto> variants=new ArrayList<>();

}
