package org.fastrackit.onlineshop4shoes.unittests;

import org.fastrackit.onlineshop4shoes.domain.Cart;
import org.fastrackit.onlineshop4shoes.domain.Product;
import org.fastrackit.onlineshop4shoes.domain.User;
import org.fastrackit.onlineshop4shoes.domain.UserRole;
import org.fastrackit.onlineshop4shoes.persistence.CartRepository;
import org.fastrackit.onlineshop4shoes.service.CartService;
import org.fastrackit.onlineshop4shoes.service.ProductService;
import org.fastrackit.onlineshop4shoes.service.UserService;
import org.fastrackit.onlineshop4shoes.transfer.cart.AddedProductsToCartRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceUnitTest {

    private CartService cartService;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private UserService userService;
    @Mock
    private ProductService productService;
    @BeforeEach
    public void setup(){
        cartService = new CartService(cartRepository, userService, productService);
    }

    @Test
    public void addProductsToCart_whenNewUser_thenNoErrorsIsThrow(){


        when(cartRepository.findById(anyLong())).thenReturn(Optional.empty());

        User user = new User();
        user.setId(1);
        user.setRole(UserRole.CUSTOMER.name());
        user.setFirstName("Test First Name");
        user.setLastName("Test Last Name");

        when(userService.getUser(anyLong())).thenReturn(user);

        Product product = new Product();
        product.setId(3);

        when(productService.getProduct(anyLong())).thenReturn(product);

        when(cartRepository.save(any(Cart.class))).thenReturn(null);

        AddedProductsToCartRequest request = new AddedProductsToCartRequest();
        request.setProductsIds(Collections.singletonList(product.getId()));

        cartService.addedProductsToCart(user.getId(), request);

        verify(cartRepository).findById(anyLong());
        verify(userService).getUser(anyLong());
        verify(productService).getProduct(anyLong());
        verify(cartRepository).save(any(Cart.class));

    }
}
