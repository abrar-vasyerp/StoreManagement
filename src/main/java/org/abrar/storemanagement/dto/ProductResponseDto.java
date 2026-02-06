package org.abrar.storemanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponseDto {

    private Long productId;
    private String productName;
    private double sellingPrice;
    private int tax;
    private Long version;
    private List<ProductVariantDto> variants;
}