package com.project.bicycleshopbe.controller.authen;

import com.project.bicycleshopbe.model.business.Rating;
import com.project.bicycleshopbe.service.businnes.IRatingService;
import com.project.bicycleshopbe.service.permission.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/auth/rating")
public class RatingRestController {

    @Autowired
    private IRatingService ratingService;

    @Autowired
    private IUserService userService;

    @GetMapping("/public")
    public ResponseEntity<?> getAllRatings(@RequestParam(name = "productId", defaultValue = "0") Long productId,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "0") int size
    ) {
        if (page < 0 || size < 0) {
            page = 0;
            size = 0;
        }
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreate"));
        Page<Rating> ratings = ratingService.findAllRatingsByProductId(productId, pageRequest);
        if (ratings.isEmpty()) {
            return ResponseEntity.status(404).body("Chưa có đánh giá!");
        }
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllRatingAuth(@PathVariable(name = "userId") Long userId,
                                              @RequestParam(name = "productId", defaultValue = "0") Long productId,
                                              @RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "0") int size
    ) {
        if (page < 0 || size < 0) {
            page = 0;
            size = 0;
        }
        Page<Rating> ratings = ratingService.findAllRatingsByProductIdAndOrderByUser(productId, userId, PageRequest.of(page, size));
        if (ratings.isEmpty()) {
            return ResponseEntity.status(404).body("Chưa có đánh giá!");
        }
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<?> getRatingById(@PathVariable(name = "ratingId") Long ratingId) {
        Rating rating = ratingService.findById(ratingId);
        if (rating == null) {
            return ResponseEntity.status(404).body("Không tìm thấy thông tin đánh giá!");
        }
        return ResponseEntity.ok(rating);
    }

    @PostMapping
    public ResponseEntity<?> addRating(@RequestBody List<Rating> ratings, @RequestParam(name = "userId") Long userId) {
        var user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.status(404).body("Không tìm thấy người dùng!");
        }
        for (Rating rating : ratings) {
            rating.setUser(user);
            rating.setDateCreate(LocalDateTime.now());
            System.out.println(rating);
            ratingService.save(rating);
        }
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<?> updateRating(@RequestBody Rating rating) {
        rating.setDateCreate(LocalDateTime.now());
        ratingService.save(rating);
        return ResponseEntity.status(201).build();
    }
}
