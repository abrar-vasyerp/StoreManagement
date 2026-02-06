package org.abrar.storemanagement.service.variant;

import lombok.RequiredArgsConstructor;
import org.abrar.storemanagement.dto.ProductVariantDto;
import org.abrar.storemanagement.entity.Product;
import org.abrar.storemanagement.entity.ProductVariant;
import org.abrar.storemanagement.repository.ProductRepository;
import org.abrar.storemanagement.repository.ProductVariantRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductVariantImpl implements ProductVariantService{
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    public ProductVariantDto addVariant(Long productId, ProductVariantDto dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductVariant variant = new ProductVariant();
        variant.setVariantName(dto.getVariantName());
        variant.setSellingPrice(dto.getVariantPrice());
        variant.setProduct(product);

        return mapToDto(productVariantRepository.save(variant));
    }
    public ProductVariantDto updateVariant(Long variantId, ProductVariantDto dto) {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        variant.setVariantName(dto.getVariantName());
        variant.setSellingPrice(dto.getVariantPrice());

        return mapToDto(productVariantRepository.save(variant));
    }
    public void deleteVariant(Long variantId) {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        Product parentProduct = variant.getProduct();
        if (parentProduct != null && parentProduct.getProductVariants() != null) {
            parentProduct.getProductVariants().remove(variant);
            productRepository.save(parentProduct); // <-- make JPA remove orphan
        } else {
            productVariantRepository.deleteById(variantId);
        }
    }


    @Override
    public ProductVariantDto getVariantById(Long variantId) {
        ProductVariant variant=productVariantRepository.findVariantById(variantId);

       return mapToDto(variant);

    }

    private ProductVariantDto mapToDto(ProductVariant variant) {
        ProductVariantDto dto = new ProductVariantDto();
        dto.setVariantId(variant.getVariantId());
        dto.setVariantName(variant.getVariantName());
        dto.setVariantPrice(variant.getSellingPrice());
        dto.setProductId(variant.getProduct().getProductId());
        return dto;
    }
}
