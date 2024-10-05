package org.mufasadev.ecommerce.project.controller;

import lombok.RequiredArgsConstructor;
import org.mufasadev.ecommerce.project.models.Cart;
import org.mufasadev.ecommerce.project.payload.CartDTO;
import org.mufasadev.ecommerce.project.repository.CartRepository;
import org.mufasadev.ecommerce.project.service.CartService;
import org.mufasadev.ecommerce.project.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final AuthUtils authUtils;
    private final CartRepository cartRepository;

    @PostMapping("carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable("productId") Long productId, @PathVariable("quantity") Integer quantity) {
        CartDTO cartDTO = cartService.addProductToCart(productId,quantity);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @GetMapping("/admin/carts")
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<CartDTO> cartDTOS = cartService.getAllCarts();
        return new ResponseEntity<List<CartDTO>>(cartDTOS, HttpStatus.FOUND);
    }

    @GetMapping("user/carts/cart")
    public ResponseEntity<CartDTO> getCartsByUser() {
        String userEmail = authUtils.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(userEmail);
        CartDTO cartDTO = cartService.getCart(userEmail,cart.getCartId());
        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);
    }

    @PutMapping("user/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCart(@PathVariable("productId") Long productId, @PathVariable("operation") String operation) {
        CartDTO cartDTO = cartService.updateCartProductQuantity(productId,operation.equalsIgnoreCase("delete") ? -1 : 1);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @DeleteMapping("user/cart/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable("cartId") Long cartId, @PathVariable("productId") Long productId){
        String status = cartService.deleteProductFromCart(cartId,productId);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}