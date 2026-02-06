package org.abrar.storemanagement.controller;

import lombok.RequiredArgsConstructor;
import org.abrar.storemanagement.dto.ApiResponse;
import org.abrar.storemanagement.dto.ProductRequestDto;
import org.abrar.storemanagement.dto.ProductResponseDto;
import org.abrar.storemanagement.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
            @Valid @RequestBody ProductRequestDto productDto) {

        ProductResponseDto saved = productService.createProduct(productDto);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product Added", saved)
        );
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts() {

        List<ProductResponseDto> productList =
                productService.getAllActiveProducts();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "All Products", productList)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductById(
            @PathVariable Long id) {

        ProductResponseDto product =
                productService.getActiveProductById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product found", product)
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProductById(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto updateDto) {

        ProductResponseDto product =
                productService.updateProduct(id, updateDto);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Updated successfully", product)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductById(
            @PathVariable Long id) {

        productService.softDeleteProduct(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product Deleted Successfully", null)
        );
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProductsWithFilter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        List<ProductResponseDto> products = productService.getProductsByFilter(
                name, minPrice, maxPrice, pageNumber, pageSize
        );

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Filtered Products", products)
        );
    }
}
