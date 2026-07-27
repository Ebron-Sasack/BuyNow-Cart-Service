package com.buynow.cart_service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private Long id;

    private Long userId;

    private BigDecimal totalAmount;

    private List<CartItemResponse> cartItems;
}