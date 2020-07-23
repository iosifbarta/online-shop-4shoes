package org.fastrackit.onlineshop4shoes;

import org.fastrackit.onlineshop4shoes.domain.Product;
import org.fastrackit.onlineshop4shoes.service.ProductService;
import org.fastrackit.onlineshop4shoes.transfer.SaveProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
class ProductServiceIntegrationTests {


    @Autowired
    private ProductService productService;

    @Test
    void createProduct_whenValidRequest_thenReturnCreatedProduct() {

        SaveProductRequest request = new SaveProductRequest();
        request.setBrandName("Epica");
        request.setPrice(149);
        request.setGender("F");
        request.setSize(37);
        request.setShoeCode("HM207-302 05-N");
        request.setQuantity(4);


        Product product = productService.createProduct(request);

        assertThat(product, notNullValue());
        assertThat(product.getId(), greaterThan(0L));
        assertThat(product.getBrandName(), is(request.getBrandName()));
        assertThat(product.getPrice(), is(request.getPrice()));
        assertThat(product.getSize(), is(request.getSize()));
        assertThat(product.getGender(), is(request.getGender()));
        assertThat(product.getShoeCode(), is(request.getShoeCode()));
        assertThat(product.getQuantity(), is(request.getQuantity()));
    }

    @Test
    void  createProduct_whenMissingMandatoryProperties_thenThrowException(){

        SaveProductRequest request = new SaveProductRequest();

        try {
            productService.createProduct(request);
        } catch (Exception e) {
            assertThat("Unexpected exception thrown", e instanceof ConstraintViolationException);
        }
    }

    @Test
    void createProduct_whenMissingBrandName_thenThrowException(){

        SaveProductRequest request = new SaveProductRequest();
        request.setPrice(149);
        request.setGender("F");
        request.setSize(38);
        request.setShoeCode("HM9052-300- 804 05-N");
        request.setQuantity(7);

        try {
            Product product = productService.createProduct(request);

            assertThat(product, notNullValue());
            assertThat(product.getId(), greaterThan(0L));
            assertThat(product.getPrice(), is(request.getPrice()));
            assertThat(product.getGender(), is(request.getGender()));
            assertThat(product.getSize(), is(request.getSize()));
            assertThat(product.getShoeCode(), is(request.getShoeCode()));
            assertThat(product.getQuantity(), is(request.getQuantity()));

        } catch (Exception e) {
            assertThat("Unexpected exception thrown", e instanceof ConstraintViolationException);
        }
    }

    @Test
    void createProduct_whenMissingSize_thenThrowException (){

        SaveProductRequest request = new SaveProductRequest();
        request.setPrice(149);
        request.setGender("F");
        request.setBrandName("Flavia Passini");
        request.setShoeCode("HM9052-300- 804 05-N");
        request.setQuantity(7);

        try {
            Product product = productService.createProduct(request);

            assertThat(product, notNullValue());
            assertThat(product.getId(), greaterThan(0L));
            assertThat(product.getBrandName(), is(request.getBrandName()));
            assertThat(product.getPrice(), is(request.getPrice()));
            assertThat(product.getGender(), is(request.getGender()));
            assertThat(product.getShoeCode(), is(request.getShoeCode()));
            assertThat(product.getQuantity(), is(request.getQuantity()));

        } catch (Exception e) {
            assertThat("Unexpected exception thrown", e instanceof ConstraintViolationException);
        }
    }

    @Test
    void createProduct_whenPriceIsNegative_thenThrowException(){

        SaveProductRequest request = new SaveProductRequest();
        request.setBrandName("Epica");
        request.setPrice(-2);
        request.setGender("F");
        request.setSize(37);
        request.setShoeCode("HM207-302 05-N");
        request.setQuantity(4);

        try {
            Product product = productService.createProduct(request);

            assertThat(product, notNullValue());
            assertThat(product.getId(), greaterThan(0L));
            assertThat(product.getBrandName(), is(request.getBrandName()));
            assertThat(product.getPrice(), greaterThan(0D));
            assertThat(product.getSize(), is(request.getSize()));
            assertThat(product.getGender(), is(request.getGender()));
            assertThat(product.getShoeCode(), is(request.getShoeCode()));
            assertThat(product.getQuantity(), is(request.getQuantity()));
        } catch (Exception e) {
            assertThat("Unexpected exception thrown", e instanceof ConstraintViolationException);
        }
    }
}
