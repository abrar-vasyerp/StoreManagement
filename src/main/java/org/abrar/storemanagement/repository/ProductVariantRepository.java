package org.abrar.storemanagement.repository;

import org.abrar.storemanagement.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant,Long> {
    @Query(value = """
        SELECT * 
        FROM product_variant 
        WHERE variant_id = :id
        """, nativeQuery = true)
    ProductVariant findVariantById(@Param("id") Long id);

    @Modifying
    @Query(value = """
        DELETE FROM product_variant
        WHERE variant_id = :id
        """, nativeQuery = true)
    int deleteVariantById(@Param("id") Long id);

    @Modifying
    @Query(value = """
        UPDATE product_variant
        SET variant_name = :variantName,
            selling_price = :sellingPrice,
            product_id = :productId
        WHERE variant_id = :id
        """, nativeQuery = true)
    int updateVariant(
            @Param("id") Long id,
            @Param("variantName") String variantName,
            @Param("sellingPrice") double sellingPrice,
            @Param("productId") Long productId
    );
}
