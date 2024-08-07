package com.project.bicycleshopbe.controller.publics;

import com.project.bicycleshopbe.dto.ProductAndPriceOfProductDTO;
import com.project.bicycleshopbe.model.business.Pricing;
import com.project.bicycleshopbe.model.business.Product;
import com.project.bicycleshopbe.service.businnes.*;
import com.project.bicycleshopbe.service.businnes.Impl.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<?> getAllProducts(@RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "nameSearch", defaultValue = "") String nameSearch,
                                                        @RequestParam(name = "categoryName", defaultValue = "") String categoryName,
                                                        @RequestParam(name = "familyName", defaultValue = "") String familyName,
                                                        @RequestParam(name = "brandName", defaultValue = "") String brandName,
                                                        @RequestParam(name = "priceBefore", defaultValue = "0") Double priceBefore,
                                                        @RequestParam(name = "priceAfter", defaultValue = "9999999999") Double priceAfter
    ) {
        if (page < 0) {
            page = 0;
        }

        Page<Product> products = productService.searchAllByProductNameAndByProductFamilyNameAndCategoryNameAndBrandNameAndPriceBetween(nameSearch, categoryName, brandName, familyName, priceBefore, priceAfter, PageRequest.of(page, 12));
        if (products.isEmpty()) {
            return ResponseEntity.status(404).body("Không có sản phẩm nào được tìm thấy!");
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/new-products")
    public ResponseEntity<?> getAllProducts() {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "date_create"));
        Page<Product> products = productService.searchAllByProductNameAndByProductFamilyNameAndCategoryNameAndBrandNameAndPriceBetween("", "", "", "", 0D, 9999999999D, pageRequest);
        if (products.isEmpty()) {
            return ResponseEntity.status(404).body("Không có sản phẩm nào được tìm thấy!");
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getAllPriceOfProduct(@PathVariable(name = "id") Long id) {
        Product product = productService.findById(id);
        List<Pricing> pricingList = priceOfProductService.searchAllByProductId(id);
        if (product == null) {
            return ResponseEntity.status(404).body("Không tìm thấy thông tin sản phẩm!");
        }
        if (pricingList.isEmpty()) {
            return ResponseEntity.status(404).body("Không tìm thấy thông tin sản phẩm!");
        }
        ProductAndPriceOfProductDTO productAndPriceOfProductDTO = new ProductAndPriceOfProductDTO(product, pricingList);
        return new ResponseEntity<>(productAndPriceOfProductDTO, HttpStatus.OK);
    }

    @GetMapping("related-products")
    public ResponseEntity<?> getAllRelatedProducts(@RequestParam(name = "categoryName", defaultValue = "") String categoryName
    ){
        Page<Product> products = productService.searchAllByProductFamilyCategoryCategoryNameContaining(categoryName, PageRequest.of(0,5));
        if (products.isEmpty()) {
            return ResponseEntity.status(404).body("Không có sản phẩm nào được tìm thấy!");
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable(name = "id") Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.status(404).body("Không tìm thấy thông tin sản phẩm!");
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
