package com.project.bicycleshopbe.controller.authen;

import com.project.bicycleshopbe.model.business.Advertisement;
import com.project.bicycleshopbe.service.businnes.Impl.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/auth/advertisements")
public class AdvertisementRestController {

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping("/public")
    public ResponseEntity<?> findAll() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "dateCreate"));
        Page<Advertisement> advertisements = advertisementService.findAll(pageRequest);
        if (advertisements.isEmpty()) {
            return ResponseEntity.status(404).body("Không tìm thấy quảng cáo!");
        }
        return ResponseEntity.ok(advertisements);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody List<Advertisement> advertisements) {
        if (advertisements.isEmpty()) {
            return ResponseEntity.status(400).body("Lỗi cú pháp!");
        }
        for (Advertisement advertisement : advertisements) {
            advertisement.setDateCreate(LocalDateTime.now());
            advertisementService.save(advertisement);
        }
        return ResponseEntity.status(201).body("Thêm mới quảng cáo thành công!");
    }

    @PutMapping("/updateAdList")
    public ResponseEntity<?> update(@RequestBody List<Advertisement> advertisements) {
        if (advertisements.isEmpty()) {
            return ResponseEntity.status(400).body("Lỗi cú pháp!");
        }
        for (Advertisement advertisement : advertisements) {
            advertisement.setDateCreate(LocalDateTime.now());
            advertisementService.save(advertisement);
        }
        return ResponseEntity.status(201).body("Thay đổi quảng cáo thành công");
    }
}
