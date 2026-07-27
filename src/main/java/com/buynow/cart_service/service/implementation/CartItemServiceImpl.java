package com.buynow.cart_service.service.implementation;

import com.buynow.cart_service.dto.response.CartItemResponse;
import com.buynow.cart_service.dto.response.ProductResponse;
import com.buynow.cart_service.entity.Cart;
import com.buynow.cart_service.entity.CartItem;
import com.buynow.cart_service.exception.ResourceNotFoundException;
import com.buynow.cart_service.feign.CartFeignClient;
import com.buynow.cart_service.repository.CartItemRepository;
import com.buynow.cart_service.repository.CartRepository;
import com.buynow.cart_service.service.CartItemService;
import com.buynow.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final CartFeignClient cartFeignClient;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void addItemToCart(Long cartId, Long productId, Integer quantity) {


        Cart cart = cartRepository.findByUserId(cartId)
                .orElseGet(() -> {

                    Cart newCart = new Cart();
                    newCart.setUserId(cartId);
                    return cartRepository.save(newCart);
                });

        ProductResponse product = cartFeignClient.getProductById(productId).getData();

        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);




        if (cartItem == null) {

            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductId(productId);
            cartItem.setProductName(product.getName());
            if(!product.getImages().isEmpty()) {
                cartItem.setProductImage(product.getImages().getFirst().getImageUrl());
            }
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setQuantity(quantity);
            cartItem.updateTotalPrice();

            cart.addItem(cartItem);

        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.updateTotalPrice();
        }

        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void removeItemFromCart( Long cartId, Long productId) {
        Cart cart = cartService.getCartEntity(cartId);
        CartItem itemToRemove = getCartItemEntity(cart.getId(), productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void updateItemQuantity(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartService.getCartEntity(cartId);
        CartItem item = cart.getCartItems()
                .stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        item.setQuantity(quantity);
        item.updateTotalPrice();
        cart.updateTotalAmount();
        cartItemRepository.save(item);
        cartRepository.save(cart);
    }


    @Override
    public CartItemResponse getCartItem(Long cartId, Long productId) {
        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cartId, productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        return modelMapper.map(cartItem, CartItemResponse.class);
    }

    private CartItem getCartItemEntity(Long cartId, Long productId){
       return cartItemRepository
                .findByCartIdAndProductId(cartId, productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));
    }

}
