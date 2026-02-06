package org.abrar.storemanagement.dto;

import lombok.Data;

@Data
public class ProductVariantDto {
    private Long variantId;
    private String variantName;
    private double variantPrice;
    private Long productId;
}