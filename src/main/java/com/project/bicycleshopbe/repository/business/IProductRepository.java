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
    Page<Product> searchAllByProductNameContaining(String name, Pageable pageable);

    Page<Product> searchAllByProductCodeContainingOrProductNameContainingOrBrandBrandNameContaining(String productCode, String productName, String brandName, Pageable pageable);

    Page<Product> searchAllByProductFamilyCategoryCategoryNameContaining(String categoryName, Pageable pageable);

    Page<Product> searchAllByProductNameContainingAndProductFamilyFamilyNameContainingAndProductFamilyCategoryCategoryNameContaining(String productName, String familyName, String categoryName, Pageable pageable);

    Page<Product> searchAllByProductNameContainingAndProductFamilyFamilyNameContainingAndProductFamilyCategoryCategoryNameContainingAndBrandBrandNameContaining(String productName, String familyName, String categoryName, String brandName, Pageable pageable);

    @Query(value = "select distinct p.product_id, p.product_desc, p.product_code, p.product_name, p.brand_id, " +
            "p.family_id, p.content, p.delete_flag, p.date_create " +
            "from products p join pricings pr on p.product_id = pr.product_id " +
            "join brands b on p.brand_id = b.brand_id " +
            "join product_families f on p.family_id = f.family_id " +
            "join categories ca on b.category_id = ca.category_id " +
            "where p.product_name like %:productName% and " +
            "ca.category_name like %:categoryName% and " +
            "b.brand_name like %:brandName% and " +
            "f.family_name like %:familyName% and " +
            "pr.price between :priceBefore and :priceAfter " +
            "order by p.date_create desc", nativeQuery = true)
    Page<Product> searchAllByProductNameAndCategoryNameAndBrandNameAndFamilyNameAndPriceBetween(@Param("productName") String productName,
            @Param("categoryName") String categoryName, @Param("brandName") String brandName,
            @Param("familyName") String familyName, @Param("priceBefore") Double priceBefore,
            @Param("priceAfter") Double priceAfter, Pageable pageable);

    @Query(value = "SELECT p.* from products p JOIN pricings pr ON p.product_id = pr.product_id " +
            "WHERE pr.price_id = :priceId", nativeQuery = true)
    Product getProductByPriceId(@Param("priceId") Long priceId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE products SET delete_flag = 0 WHERE product_id = :productId", nativeQuery = true)
    void deleteProductByProductId(@Param("productId") Long productId);

}
