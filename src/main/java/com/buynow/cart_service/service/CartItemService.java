package com.buynow.cart_service.service;

import com.buynow.cart_service.dto.request.RemoveCartItemRequest;
import com.buynow.cart_service.dto.request.UpdateCartItemRequest;
import com.buynow.cart_service.dto.response.CartItemResponse;

public interface CartItemService {

    void addItemToCart(Long cartId, Long productId, Integer quantity);

    void removeItemFromCart(Long cartId, Long productId);

    void updateItemQuantity(Long cartId, Long productId, Integer quantity);

    CartItemResponse getCartItem(Long cartId, Long productId);
}
