package org.abrar.storemanagement.repository;

import org.abrar.storemanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Modifying
    @Transactional
    @Query("update Product p set p.isDeleted=true where p.productId=:id")
    int softDeleteProductByProductId(@Param("id") Long productId);

    List<Product> findByIsDeletedFalse();

    List<Product> findByProductNameContainingIgnoreCaseAndIsDeletedFalse(String name);

    Optional<Product> findByProductIdAndIsDeletedFalse(Long productId);

    Optional<Product> findByProductId(Long productId);

    @Query(value = """
        SELECT *
        FROM product p
        WHERE p.is_deleted = false
          AND (:name IS NULL OR LOWER(p.product_name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:minPrice IS NULL OR p.selling_price >= :minPrice)
          AND (:maxPrice IS NULL OR p.selling_price <= :maxPrice)
        ORDER BY p.product_name ASC
        LIMIT :limit OFFSET :offset
        """, nativeQuery = true)
    List<Product> findAllWithFiltersSortedByName(
            @Param("name") String name,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    boolean existsByProductNameIgnoreCaseAndIsDeletedFalse(String productName);

}
