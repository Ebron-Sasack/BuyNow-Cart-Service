package com.buynow.cart_service.service;

import com.buynow.cart_service.dto.response.CartResponse;
import com.buynow.cart_service.entity.Cart;

public interface CartService {

    CartResponse getCart(Long cartId);

    CartResponse getCartByUserId(Long userId);

    CartResponse createCart(Long userId);

    CartResponse clearCart(Long userId);

    void deleteCart(Long userId);

    Cart getCartEntity(Long cartId);
}