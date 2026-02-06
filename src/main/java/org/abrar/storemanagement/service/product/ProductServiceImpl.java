package org.abrar.storemanagement.service.product;

import lombok.RequiredArgsConstructor;
import org.abrar.storemanagement.dto.ProductRequestDto;
import org.abrar.storemanagement.dto.ProductResponseDto;
import org.abrar.storemanagement.dto.ProductVariantDto;
import org.abrar.storemanagement.entity.Product;
import org.abrar.storemanagement.entity.ProductVariant;
import org.abrar.storemanagement.exception.VersionMismatchException;
import org.abrar.storemanagement.repository.OrderItemRepository;
import org.abrar.storemanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto) {

        if (dto == null) {
            throw new RuntimeException("Product body is required");
        }

        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setSellingPrice(dto.getSellingPrice());
        product.setTax(dto.getTax());
        product.setDeleted(false);
        product.setVersion(0L);

        List<ProductVariant> productVariants = new ArrayList<>();

        if (dto.getVariants() != null && !dto.getVariants().isEmpty()) {

            for (ProductVariantDto vDto : dto.getVariants()) {
                ProductVariant variant = new ProductVariant();
                variant.setVariantName(vDto.getVariantName());
                variant.setSellingPrice(vDto.getVariantPrice());
                variant.setProduct(product);
                productVariants.add(variant);
            }
        } else {

            ProductVariant defaultVariant = new ProductVariant();
            defaultVariant.setVariantName(dto.getProductName());
            defaultVariant.setSellingPrice(dto.getSellingPrice());
            defaultVariant.setProduct(product);
            productVariants.add(defaultVariant);
        }

        product.setProductVariants(productVariants);
        Product saved = productRepository.save(product);

        return mapToResponse(saved);
    }

    @Override
    public List<ProductResponseDto> getAllActiveProducts() {

        return productRepository
                .findByIsDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Override
    public List<ProductResponseDto> searchProductsByName(String name) {

        return productRepository
                .findByProductNameContainingIgnoreCaseAndIsDeletedFalse(name)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    public void softDeleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        if (product.isDeleted()) {
            throw new RuntimeException("Product already deleted");
        }
        int updated = productRepository.softDeleteProductByProductId(productId);

        if (updated == 0) {
            throw new RuntimeException("Product not found so can't deleted");
        }
    }

    @Override
    public ProductResponseDto getActiveProductById(Long productId) {
        Product product = productRepository
                .findByProductIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapToResponse(product);
    }

    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto dto) {

        Product product = productRepository
                .findByProductIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getVersion().equals(dto.getVersion())) {
            throw new VersionMismatchException(
                    "Product has been modified by another transaction. Please refresh and retry."
            );
        }
        product.setProductName(dto.getProductName());
        product.setSellingPrice(dto.getSellingPrice());
        product.setTax(dto.getTax());

        return mapToResponse(productRepository.save(product));
    }


    public void printTopSellingProductsLastNDays(int n) {
        LocalDateTime nDaysAgo = LocalDateTime.now().minusDays(n);
        List<Object[]> topSell = orderItemRepository.findTopSellingProducts(nDaysAgo);

        for (Object[] row : topSell) {
            Product product = (Product) row[0];
            Double totalSold = (Double) row[1];
            System.out.println(product.getProductName()+" sold: "+totalSold);
        }
    }

    public List<ProductResponseDto> getProductsByFilter(
            String name,
            Double minPrice,
            Double maxPrice,
            int pageNumber,
            int pageSize
    ) {

        int offset = (pageNumber - 1) * pageSize;
        int limit = pageSize;
        List<Product> products = productRepository.findAllWithFiltersSortedByName(
                name, minPrice, maxPrice, limit, offset
        );

        return products.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProductResponseDto mapToResponse(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setSellingPrice(product.getSellingPrice());
        dto.setTax(product.getTax());
        dto.setVersion(product.getVersion());

        List<ProductVariantDto> variantDtos = new ArrayList<>();
        if (product.getProductVariants() != null) {
            for (ProductVariant v : product.getProductVariants()) {
                ProductVariantDto vDto = new ProductVariantDto();
                vDto.setProductId(v.getProduct().getProductId());
                vDto.setVariantId(v.getVariantId());
                vDto.setVariantName(v.getVariantName());
                vDto.setVariantPrice(v.getSellingPrice());
                variantDtos.add(vDto);
            }
        }

        dto.setVariants(variantDtos);

        return dto;
    }

    public boolean productNameExists(String name) {
        return productRepository
                .existsByProductNameIgnoreCaseAndIsDeletedFalse(name);
    }
}
