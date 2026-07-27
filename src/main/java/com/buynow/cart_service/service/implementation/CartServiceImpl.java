package com.buynow.cart_service.service.implementation;

import com.buynow.cart_service.dto.response.CartResponse;
import com.buynow.cart_service.entity.Cart;
import com.buynow.cart_service.exception.AlreadyExistsException;
import com.buynow.cart_service.exception.ResourceNotFoundException;
import com.buynow.cart_service.repository.CartItemRepository;
import com.buynow.cart_service.repository.CartRepository;
import com.buynow.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;

    @Override
    public CartResponse getCart(Long cartId) {
        Cart cart = getCartEntity(cartId);
        return convertToDto(cart);
    }

    @Override
    public CartResponse getCartByUserId(Long userId) {
        Cart cart = getCartEntityByUser(userId);
        return convertToDto(cart);
    }

    @Override
    @Transactional
    public CartResponse createCart(Long userId) {

        if (cartRepository.existsByUserId(userId)) {
            throw new AlreadyExistsException(
                    "Cart already exists for user id : " + userId);
        }

        Cart cart = new Cart();
        cart.setUserId(userId);
        Cart savedCart = cartRepository.save(cart);

        return convertToDto(savedCart);
    }

    @Override
    @Transactional
    public CartResponse clearCart(Long userId) {
        Cart cart = getCartEntityByUser(userId);
        cart.getCartItems().clear();
        cart.updateTotalAmount();
        Cart updatedCart = cartRepository.save(cart);
        return convertToDto(updatedCart);
    }

    @Override
    @Transactional
    public void deleteCart(Long userId) {
        Cart cart = getCartEntityByUser(userId);
        cartRepository.delete(cart);
    }

    private CartResponse convertToDto(Cart cart){
        return modelMapper.map(cart,CartResponse.class);
    }

    @Override
    public Cart getCartEntity(Long cartId){
        return cartRepository.findById(cartId).orElseThrow(
                ()-> new ResourceNotFoundException("Cart Not Found")
        );
    }

    private Cart getCartEntityByUser(Long userId){
        return cartRepository.findByUserId(userId).orElseThrow(
                ()-> new ResourceNotFoundException("Cart Not Found")
        );
    }
}
