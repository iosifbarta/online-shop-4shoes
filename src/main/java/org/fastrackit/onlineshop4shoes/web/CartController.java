package org.fastrackit.onlineshop4shoes.web;


import org.fastrackit.onlineshop4shoes.domain.Cart;
import org.fastrackit.onlineshop4shoes.service.CartService;
import org.fastrackit.onlineshop4shoes.transfer.cart.AddedProductsToCartRequest;
import org.fastrackit.onlineshop4shoes.transfer.cart.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> addProductsToCart(
            @PathVariable long userId, @Valid @RequestBody AddedProductsToCartRequest request) {
        cartService.addedProductsToCart(userId, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable long userId) {
        CartResponse cart = cartService.getCart(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
