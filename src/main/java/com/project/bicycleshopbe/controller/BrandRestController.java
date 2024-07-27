package com.project.bicycleshopbe.controller;

import com.project.bicycleshopbe.model.business.Brand;
import com.project.bicycleshopbe.service.businnes.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/brands")
@CrossOrigin("*")
public class BrandRestController {

    @Autowired
    private IBrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> brands = brandService.findAll();
        if (brands.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<List<Brand>> getBrandByCategoryId(@PathVariable String categoryName) {
        List<Brand> brands = brandService.findAllByCategoryName(categoryName);
        if (brands.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(brands);
    }

    @PostMapping
    public ResponseEntity<?> createBrand(@RequestBody Brand brand) {
        brandService.save(brand);
        return new ResponseEntity<>(200, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable(name = "id") Long brandId) {
        brandService.remove(brandId);
        return new ResponseEntity<>(200, HttpStatus.OK);
    }
}
