package com.project.bicycleshopbe.controller;

import com.project.bicycleshopbe.model.business.Bill;
import com.project.bicycleshopbe.model.business.BillItem;
import com.project.bicycleshopbe.model.business.CartItem;
import com.project.bicycleshopbe.model.permission.AppUser;
import com.project.bicycleshopbe.service.businnes.IBillService;
import com.project.bicycleshopbe.service.businnes.ICartItemService;
import com.project.bicycleshopbe.service.permission.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api/auth/shopping-cart")
public class ShoppingCartRestController {

    @Autowired
    private IBillService billService;

    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<?> getAllCartOfUser(@RequestParam(name = "userId") Long userId) {
        System.out.println(userId);
        Set<CartItem> cartItems = cartItemService.findAllCartByUserId(userId);
        if (cartItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(200).body(cartItems);
    }

    @PostMapping
    public ResponseEntity<?> addCartItem(@RequestParam(name = "userId") Long userId, @RequestBody CartItem cartItem) {
        var user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        System.out.println(cartItem);
        cartItem.setAppUser(user);
        cartItemService.save(cartItem);

        return ResponseEntity.status(201).body(cartItem);
    }

    @DeleteMapping("/delete-from-cart")
    public ResponseEntity<?> deleteFromCart(@RequestParam(name = "priceId") Long priceId
            , @RequestParam(name = "userId") Long userId){
        cartItemService.deleteCartItemByPriceIdAndUserId(priceId, userId);

        return ResponseEntity.status(201).body("Xóa sản phẩm khỏi giỏ hàng thành công!");
    }

    @PutMapping
    public ResponseEntity<?> updateCartItem(@RequestParam(name = "priceId") Long priceId,
                                            @RequestParam(name = "userId") Long userId,
                                            @RequestBody CartItem cartItem) {
        cartItemService.updateQuantityCartItemByPriceIdAndUserId(priceId, userId, cartItem.getQuantity());

        return ResponseEntity.status(201).body(cartItem);
    }

    @PostMapping("/pay")
    public ResponseEntity<?> saveBill(@RequestParam(name = "userId") Long userId  , @RequestBody Bill bill) {
        System.out.println(bill);
        AppUser user = null;
        if (userId != null) {
            user = userService.findById(userId);
        }

        bill.setAppUser(user);
        bill.setDateCreate(LocalDateTime.now());
        bill.setPaid(false);

        // Thiết lập mối quan hệ hai chiều
        for (BillItem item : bill.getBillItems()) {
            item.setBill(bill);
        }

        billService.save(bill);
        if (userId != null) {
            for (BillItem billItem : bill.getBillItems()) {
                cartItemService.deleteCartItemByPriceIdAndUserId(billItem.getPricing().getPriceId(), userId);
            }
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
