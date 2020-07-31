package org.fastrackit.onlineshop4shoes.steps;

import org.fastrackit.onlineshop4shoes.service.ProductService;
import org.fastrackit.onlineshop4shoes.transfer.product.ProductResponse;
import org.fastrackit.onlineshop4shoes.transfer.product.SaveProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class ProductTestSteps {


    @Autowired
    private ProductService productService;

    public ProductResponse createProduct() {
        SaveProductRequest request = new SaveProductRequest();
        request.setBrandName("Gabor");
        request.setPrice(219);
        request.setGender("F");
        request.setSize(36);
        request.setShoeCode("GB72510 05-N");
        request.setQuantity(7);

        ProductResponse product = productService.createProduct(request);

        assertThat(product, notNullValue());
        assertThat(product.getId(), greaterThan(0L));
        assertThat(product.getBrandName(), is(request.getBrandName()));
        assertThat(product.getPrice(), is(request.getPrice()));
        assertThat(product.getSize(), is(request.getSize()));
        assertThat(product.getGender(), is(request.getGender()));
        assertThat(product.getShoeCode(), is(request.getShoeCode()));
        assertThat(product.getQuantity(), is(request.getQuantity()));

        return product;
    }
}
