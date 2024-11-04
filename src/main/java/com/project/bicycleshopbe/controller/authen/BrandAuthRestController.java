package com.project.bicycleshopbe.controller.authen;

import com.project.bicycleshopbe.model.business.Brand;
import com.project.bicycleshopbe.service.businnes.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/brands")
public class BrandAuthRestController {

    @Autowired
    private IBrandService brandService;

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
