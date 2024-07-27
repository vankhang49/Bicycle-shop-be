package com.project.bicycleshopbe.controller;

import com.project.bicycleshopbe.model.business.Promotion;
import com.project.bicycleshopbe.service.businnes.Impl.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/promotions")
@CrossOrigin("*")
public class PromotionRestController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping
    public ResponseEntity<List<Promotion>> getPromotions() {
        List<Promotion> promotions = promotionService.findAll();
        if (promotions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(promotions);
    }
}
