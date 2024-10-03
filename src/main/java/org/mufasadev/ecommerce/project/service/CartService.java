package org.mufasadev.ecommerce.project.service;

import org.mufasadev.ecommerce.project.payload.CartDTO;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCart(String userEmail, Long cartId);

    CartDTO updateCartProductQuantity(Long productId, int quantity);

    String deleteProductFromCart(Long cartId, Long productId);

    void updateProductInCarts(Long cartId, Long productId);
}