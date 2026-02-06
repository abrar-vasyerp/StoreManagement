package org.abrar.storemanagement.service.product;

import org.abrar.storemanagement.dto.ProductRequestDto;
import org.abrar.storemanagement.dto.ProductResponseDto;


import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto dto);

    List<ProductResponseDto> getAllActiveProducts();

    ProductResponseDto getActiveProductById(Long productId);

    ProductResponseDto updateProduct(Long productId, ProductRequestDto dto);

    List<ProductResponseDto> searchProductsByName(String name);

    void softDeleteProduct(Long productId);

    List<ProductResponseDto> getProductsByFilter(
            String name,
            Double minPrice,
            Double maxPrice,
            int limit,
            int offset
    );
    public boolean productNameExists(String name);
}

