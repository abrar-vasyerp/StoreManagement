package org.abrar.storemanagement.service.variant;

import org.abrar.storemanagement.dto.ProductVariantDto;
import org.abrar.storemanagement.entity.ProductVariant;

public interface ProductVariantService {
    ProductVariantDto addVariant(Long productId, ProductVariantDto dto);
    ProductVariantDto updateVariant(Long variantId, ProductVariantDto dto);
    void deleteVariant(Long variantId);

    ProductVariantDto getVariantById(Long variantId);
}
