package com.project.bicycleshopbe.controller.authen;

import com.project.bicycleshopbe.model.business.Product;
import com.project.bicycleshopbe.service.businnes.Impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/products")
public class ProductAuthRestController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts(@RequestParam(name = "productCode", defaultValue = "") String productCode,
                                            @RequestParam(name = "productName", defaultValue = "") String productName,
                                            @RequestParam(name = "brandName", defaultValue = "") String brandName,
                                            @RequestParam(name = "page", defaultValue = "0") int page
    ) {
        if (page < 0) {
            page = 0;
        }
        Page<Product> products = productService.searchAllByProductCodeOrProductNameOrBrandName(productCode, productName, brandName, PageRequest.of(page, 10));
        if(products.isEmpty()) {
            return ResponseEntity.status(404).body("Không có sản phẩm nào được tìm thấy!");
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/pricing/{priceId}")
    public ResponseEntity<?> getProductByPriceId(@PathVariable Long priceId) {
        System.out.println(priceId);
        Product product = productService.getProductByPriceId(priceId);
        System.out.println(product);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
}
