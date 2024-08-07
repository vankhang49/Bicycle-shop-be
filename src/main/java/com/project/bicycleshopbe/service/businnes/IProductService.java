package com.project.bicycleshopbe.service.businnes;

import com.project.bicycleshopbe.model.business.Pricing;
import com.project.bicycleshopbe.model.business.Product;
import com.project.bicycleshopbe.model.business.ProductImage;
import com.project.bicycleshopbe.service.IGenerateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService extends IGenerateService<Product> {
    Page<Product> searchAllByProductCodeOrProductNameOrBrandName(String productCode, String productName, String brandName, Pageable pageable);

    Page<Product> searchAllByProductFamilyCategoryCategoryNameContaining(String categoryName, Pageable pageable);
    Page<Product> searchAllByProductNameAndByProductFamilyNameAndCategoryNameAndBrandName(String productName, String familyName,
                                                                              String categoryName, String brandName, Pageable pageable);
    Page<Product> searchAllByProductNameAndByProductFamilyNameAndCategoryNameAndBrandNameAndPriceBetween(String productName,String categoryName,
                                                                                                         String brandName, String familyName,
                                                                                                         Double priceBefore, Double priceAfter,
                                                                                                         Pageable pageable);
    void saveProductWithDetails(Product product, List<ProductImage> productImages, List<Pricing> pricingList);

    void updateProduct(Product product);

    Product getProductByPriceId(Long priceId);
}