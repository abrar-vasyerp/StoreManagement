package org.abrar.storemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"productVariants","deleted"})
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name="product_name")
    private String productName;

    @Column(name="tax")
    private int tax;

    @Column(name="selling_price")
    private double sellingPrice;

    @Column(name="is_deleted")
    private boolean isDeleted;

    @Column(name="created_on")
    private LocalDateTime createdOn;

    @Column(name="modified_on")
    private LocalDateTime modifiedOn;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductVariant> productVariants;

    @Version
    private Long version;

    @PrePersist
    private void onCreate(){
        createdOn=LocalDateTime.now();
        modifiedOn=LocalDateTime.now();
        isDeleted=false;
    }

    @PreUpdate
    private void onUpdate(){
        modifiedOn=LocalDateTime.now();
    }

}
