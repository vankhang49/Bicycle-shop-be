package com.project.bicycleshopbe.service.businnes.Impl;

import com.project.bicycleshopbe.model.business.Pricing;
import com.project.bicycleshopbe.model.business.Product;
import com.project.bicycleshopbe.model.business.ProductImage;
import com.project.bicycleshopbe.repository.business.IProductRepository;
import com.project.bicycleshopbe.service.businnes.IProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> searchAllByProductCodeOrProductNameOrBrandName(String productCode, String productName, String brandName, Pageable pageable) {
        return productRepository.searchAllByProductCodeContainingOrProductNameContainingOrBrandBrandNameContaining(productCode, productName, brandName, pageable);
    }

    @Override
    public Page<Product> searchAllByProductFamilyCategoryCategoryNameContaining(String categoryName, Pageable pageable) {
        return productRepository.searchAllByProductFamilyCategoryCategoryNameContaining(categoryName, pageable);
    }

    @Override
    public Page<Product> searchAllByProductNameAndByProductFamilyNameAndCategoryNameAndBrandName(String productName, String familyName, String categoryName, String brandName, Pageable pageable) {
        return productRepository.searchAllByProductNameContainingAndProductFamilyFamilyNameContainingAndProductFamilyCategoryCategoryNameContainingAndBrandBrandNameContaining(productName, familyName, categoryName, brandName, pageable);
    }

    @Override
    public Page<Product> searchAllByProductNameAndByProductFamilyNameAndCategoryNameAndBrandNameAndPriceBetween(String productName, String categoryName, String brandName, String familyName, Double priceBefore, Double priceAfter, Pageable pageable) {
        return productRepository.searchAllByProductNameAndCategoryNameAndBrandNameAndFamilyNameAndPriceBetween(productName, categoryName, brandName, familyName, priceBefore, priceAfter, pageable);
    }

    @Override
    public Product getProductByPriceId(Long priceId) {
        return productRepository.getProductByPriceId(priceId);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }


    @Override
    public void save(Product product) {
        product.setDateCreate(LocalDate.now());
        for (Pricing pricing : product.getPricingList()) {
            pricing.setProduct(product);
        }
        for (ProductImage productImage : product.getProductImages()) {
            productImage.setProduct(product);
        }
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        for (Pricing pricing : product.getPricingList()) {
            if (pricing.getProduct() == null) {
                System.out.println(pricing);
                pricing.setProduct(product);
            }
        }
        for (ProductImage productImage : product.getProductImages()) {
            if (productImage.getProduct() == null) {
                System.out.println(productImage);
                productImage.setProduct(product);
            }
        }
        productRepository.save(product);
    }


    @Transactional
    @Override
    public void saveProductWithDetails(Product product, List<ProductImage> productImages, List<Pricing> priceOfProducts) {
        for (ProductImage productImage : productImages) {
            productImage.setProduct(product);
        }
        for (Pricing pricing : priceOfProducts) {
            pricing.setProduct(product);
        }
//        product.setProductImages(productImages);

        productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }
}
