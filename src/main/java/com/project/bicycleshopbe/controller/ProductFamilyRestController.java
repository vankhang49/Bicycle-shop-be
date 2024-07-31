package com.project.bicycleshopbe.controller;

import com.project.bicycleshopbe.model.business.Category;
import com.project.bicycleshopbe.model.business.ProductFamily;
import com.project.bicycleshopbe.service.businnes.IProductFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/product-families")
public class ProductFamilyRestController {
    @Autowired
    private IProductFamilyService productFamilyService;

    @GetMapping
    public ResponseEntity<List<ProductFamily>> getAllProductFamily() {
        List<ProductFamily> productFamilies = productFamilyService.findAll();
        if (productFamilies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productFamilies);
    }

    @PostMapping
    public ResponseEntity<?> createProductFamily(@RequestBody ProductFamily productFamily) {
        productFamilyService.save(productFamily);
        return new ResponseEntity<>(200, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductFamily(@PathVariable(name = "id") Long productFamilyId) {
        productFamilyService.remove(productFamilyId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
