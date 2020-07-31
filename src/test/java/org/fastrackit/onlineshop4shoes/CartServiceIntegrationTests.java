package org.fastrackit.onlineshop4shoes;

import org.fastrackit.onlineshop4shoes.domain.User;
import org.fastrackit.onlineshop4shoes.service.CartService;
import org.fastrackit.onlineshop4shoes.steps.ProductTestSteps;
import org.fastrackit.onlineshop4shoes.steps.UserTestSteps;
import org.fastrackit.onlineshop4shoes.transfer.cart.AddedProductsToCartRequest;
import org.fastrackit.onlineshop4shoes.transfer.cart.CartResponse;
import org.fastrackit.onlineshop4shoes.transfer.product.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


@SpringBootTest
public class CartServiceIntegrationTests {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserTestSteps userTestSteps;
    @Autowired
    private ProductTestSteps productTestSteps;


    @Test
    void addProductsToCart_whenNewCart_theCartIsCreated(){
        User user = userTestSteps.createUser();
        ProductResponse product = productTestSteps.createProduct();

        AddedProductsToCartRequest cartRequest = new AddedProductsToCartRequest();

        cartRequest.setUserId(user.getId());
        cartRequest.setProductsIds(Collections.singletonList(product.getId()));

        cartService.addedProductsToCart(user.getId(), cartRequest);

        CartResponse cartResponse = cartService.getCart(user.getId());

        assertThat(cartResponse, notNullValue());
        assertThat(cartResponse.getId(), is(user.getId()));
        assertThat(cartResponse.getProducts(), notNullValue());
        assertThat(cartResponse.getProducts(), hasSize(1));
        assertThat(cartResponse.getProducts().get(0), notNullValue());
        assertThat(cartResponse.getProducts().get(0).getId(), is(product.getId()));
        assertThat(cartResponse.getProducts().get(0).getName(), is(product.getBrandName()));
        assertThat(cartResponse.getProducts().get(0).getPrice(), is(product.getPrice()));
        assertThat(cartResponse.getProducts().get(0).getImageUrl(), is(product.getImageUrl()));
    }
}
