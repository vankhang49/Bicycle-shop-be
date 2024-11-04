package com.project.bicycleshopbe.repository.business;

import com.project.bicycleshopbe.model.business.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    Page<Product> searchAllByProductCodeContainingOrProductNameContainingOrBrandBrandNameContaining(String productCode, String productName, String brandName, Pageable pageable);

    Page<Product> searchAllByProductFamilyCategoryCategoryNameContaining(String categoryName, Pageable pageable);

    Page<Product> searchAllByProductNameContainingAndProductFamilyFamilyNameContainingAndProductFamilyCategoryCategoryNameContainingAndBrandBrandNameContaining(String productName, String familyName, String categoryName, String brandName, Pageable pageable);

    @Query(
            value = "SELECT DISTINCT p.* FROM products p " +
                    "JOIN pricings pr ON p.product_id = pr.product_id " +
                    "JOIN brands b ON p.brand_id = b.brand_id " +
                    "JOIN product_families f ON p.family_id = f.family_id " +
                    "JOIN categories ca ON b.category_id = ca.category_id " +
                    "WHERE p.product_name LIKE %:productName% AND " +
                    "ca.category_name LIKE %:categoryName% AND " +
                    "b.brand_name LIKE %:brandName% AND " +
                    "f.family_name LIKE %:familyName% AND " +
                    "pr.price BETWEEN :priceBefore AND :priceAfter " +
                    "ORDER BY p.date_create DESC",
            countQuery = "SELECT COUNT(DISTINCT p.product_id) FROM products p " +
                    "JOIN pricings pr ON p.product_id = pr.product_id " +
                    "JOIN brands b ON p.brand_id = b.brand_id " +
                    "JOIN product_families f ON p.family_id = f.family_id " +
                    "JOIN categories ca ON b.category_id = ca.category_id " +
                    "WHERE p.product_name LIKE %:productName% AND " +
                    "ca.category_name LIKE %:categoryName% AND " +
                    "b.brand_name LIKE %:brandName% AND " +
                    "f.family_name LIKE %:familyName% AND " +
                    "pr.price BETWEEN :priceBefore AND :priceAfter",
            nativeQuery = true
    )
    Page<Product> searchAllByProductNameAndCategoryNameAndBrandNameAndFamilyNameAndPriceBetween(@Param("productName") String productName,
            @Param("categoryName") String categoryName, @Param("brandName") String brandName,
            @Param("familyName") String familyName, @Param("priceBefore") Double priceBefore,
            @Param("priceAfter") Double priceAfter, Pageable pageable);

    @Query(value = "SELECT p.* from products p JOIN pricings pr ON p.product_id = pr.product_id " +
            "WHERE pr.price_id = :priceId", nativeQuery = true)
    Product getProductByPriceId(@Param("priceId") Long priceId);

    Product findFirstByOrderByDateCreate();

    @Transactional
    @Modifying
    @Query(value = "UPDATE products SET delete_flag = 0 WHERE product_id = :productId", nativeQuery = true)
    void deleteProductByProductId(@Param("productId") Long productId);

}
