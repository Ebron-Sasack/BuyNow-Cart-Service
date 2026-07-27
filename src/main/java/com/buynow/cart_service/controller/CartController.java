package com.buynow.cart_service.controller;

import com.buynow.cart_service.dto.response.CartResponse;
import com.buynow.cart_service.payload.ApiResponse;
import com.buynow.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<CartResponse>> createCart(@PathVariable Long userId) {

        CartResponse cartResponse = cartService.createCart(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Cart created successfully.", cartResponse));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse<CartResponse>> getCart(@PathVariable Long cartId) {

        CartResponse cartResponse = cartService.getCart(cartId);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Cart fetched successfully.", cartResponse));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<CartResponse>> getCartByUserId(@PathVariable Long userId) {

        CartResponse cartResponse = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Cart fetched successfully.", cartResponse));
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<ApiResponse<CartResponse>> clearCart(@PathVariable Long userId) {

        CartResponse cartResponse = cartService.clearCart(userId);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Cart cleared successfully.", cartResponse));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteCart(@PathVariable Long userId) {

        cartService.deleteCart(userId);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Cart deleted successfully.", null));
    }
}