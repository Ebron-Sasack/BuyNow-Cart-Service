package com.buynow.cart_service.controller;

import com.buynow.cart_service.dto.request.AddCartItemRequest;
import com.buynow.cart_service.dto.request.RemoveCartItemRequest;
import com.buynow.cart_service.dto.request.UpdateCartItemRequest;
import com.buynow.cart_service.dto.response.CartItemResponse;
import com.buynow.cart_service.payload.ApiResponse;
import com.buynow.cart_service.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addItemToCart(@RequestBody AddCartItemRequest request) {


        cartItemService.addItemToCart(request.getUserId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Item added to cart successfully.",
                        null
                )
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateItemQuantity(@RequestBody UpdateCartItemRequest request) {

        cartItemService.updateItemQuantity(request.getUserId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Cart item updated successfully.",
                        null
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> removeItemFromCart(@RequestBody RemoveCartItemRequest request) {

        cartItemService.removeItemFromCart(request.getUserId(), request.getProductId());
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Cart item removed successfully.",
                        null
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartItemResponse>> getCartItem(
            @RequestParam Long cartId,
            @RequestParam Long productId) {

        CartItemResponse cartItemResponse =
                cartItemService.getCartItem(cartId, productId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Cart item fetched successfully.",
                        cartItemResponse
                )
        );
    }
}