package com.project.bicycleshopbe.controller;

import com.project.bicycleshopbe.dto.ProductAndPriceOfProductDTO;
import com.project.bicycleshopbe.model.business.Pricing;
import com.project.bicycleshopbe.model.business.Product;
import com.project.bicycleshopbe.service.businnes.*;
import com.project.bicycleshopbe.service.businnes.Impl.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/products")
public class ProductRestController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IProductFamilyService productFamilyService;
    @Autowired
    private IPriceOfProductService priceOfProductService;
    @Autowired
    private IColorService colorService;
    @Autowired
    private IPromotionService promotionService;
    @Autowired
    private ProductImageService productImageService;

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "nameSearch", defaultValue = "") String nameSearch,
                                                        @RequestParam(name = "categoryName", defaultValue = "") String categoryName,
                                                        @RequestParam(name = "familyName", defaultValue = "") String familyName,
                                                        @RequestParam(name = "brandName", defaultValue = "") String brandName,
                                                        @RequestParam(name = "priceBefore", defaultValue = "0") Double priceBefore,
                                                        @RequestParam(name = "priceAfter", defaultValue = "9999999999") Double priceAfter
    ) {
        System.out.println(nameSearch + "," + familyName + "," + categoryName + "," + brandName + "," + priceBefore + "," + priceAfter);
        if (page < 0) {
            page = 0;
        }
        Page<Product> products = productService.searchAllByProductNameAndByProductFamilyNameAndCategoryNameAndBrandNameAndPriceBetween(nameSearch, familyName, categoryName, brandName, priceBefore, priceAfter, PageRequest.of(page, 12));
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ProductAndPriceOfProductDTO> getAllPriceOfProduct(@PathVariable(name = "id") Long id) {
        Product product = productService.findById(id);
        List<Pricing> pricingList = priceOfProductService.searchAllByProductId(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (pricingList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ProductAndPriceOfProductDTO productAndPriceOfProductDTO = new ProductAndPriceOfProductDTO(product, pricingList);
        return new ResponseEntity<>(productAndPriceOfProductDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable(name = "id") Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        System.out.println(product);
        productService.save(product);
        return new ResponseEntity<>(200, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        System.out.println(product);
        productService.updateProduct(product);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
