package com.amar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amar.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("""
        SELECT p FROM Product p
        WHERE (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
          AND (:hasDiscount IS NULL OR 
               (:hasDiscount = TRUE AND p.discount IS NOT NULL) OR
               (:hasDiscount = FALSE AND p.discount IS NULL))
          AND (:quantityGt IS NULL OR p.quantity > :quantityGt)
          AND (
               :searchText IS NULL OR 
               LOWER(p.name) LIKE LOWER(CONCAT(CAST(:searchText AS string), '%')) OR
               LOWER(p.description) LIKE LOWER(CONCAT('%', CAST(:searchText AS string), '%'))
          )
          AND (
               :name IS NULL OR 
               LOWER(p.name) LIKE LOWER(CONCAT(CAST(:name AS string), '%'))
          )
    """)
    List<Product> filterProducts(
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("hasDiscount") Boolean hasDiscount,
        @Param("quantityGt") Integer quantityGt,
        @Param("searchText") String searchText,
        @Param("name") String name
    );
}

