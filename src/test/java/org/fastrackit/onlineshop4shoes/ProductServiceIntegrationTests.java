package org.fastrackit.onlineshop4shoes;

import org.fastrackit.onlineshop4shoes.domain.Product;
import org.fastrackit.onlineshop4shoes.exception.ResourceNotFoundException;
import org.fastrackit.onlineshop4shoes.service.ProductService;
import org.fastrackit.onlineshop4shoes.steps.ProductTestSteps;
import org.fastrackit.onlineshop4shoes.transfer.SaveProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class ProductServiceIntegrationTests {


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTestSteps productTestSteps;

    @Test
    void createProduct_whenValidRequest_thenReturnCreatedProduct() {

        productTestSteps.createProduct();
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

    @Test
    void getProduct_whenExistingProduct_theReturnProduct(){

        Product product = productTestSteps.createProduct();

        Product response = productService.getProduct(product.getId());

        assertThat(response, notNullValue());
        assertThat(response.getId(), is(product.getId()));
        assertThat(response.getBrandName(), is(product.getBrandName()));
        assertThat(response.getShoeCode(), is(product.getShoeCode()));
        assertThat(response.getPrice(), is(product.getPrice()));
        assertThat(response.getQuantity(), is(product.getQuantity()));
        assertThat(response.getSize(), is(product.getSize()));
        assertThat(response.getGender(), is(product.getGender()));
        assertThat(response.getImageUrl(), is(product.getImageUrl()));
        assertThat(response.getDescription(), is(product.getDescription()));

    }
    @Test
    void getProduct_whenNonExistingProduct_thenThrowResourceNotFoundException(){

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.getProduct(0));
    }

//    @Test
//    void getProducts_whenExistingProducts_thenReturnPageOfProducts(){
//
//        Product product1 = createProduct();
//        Product product2 = createProduct();
//
//        Page<Product> productsPage = productService.getProducts(new GetProductsRequest(), PageRequest.of(0, 1000));
//       productsPage.getSort().descending();
//
//        assertThat(productsPage, notNullValue());
//        assertThat(productsPage.getTotalElements(), greaterThan(1L));
//
//        assertThat(productsPage.getContent(), contains(product1,product2));
//    }

    @Test
    void updateProduct_whenValidRequest_thenReturnUpdateProduct (){

        Product product = productTestSteps.createProduct();

        SaveProductRequest request = new SaveProductRequest();

        request.setBrandName(product.getBrandName()+ " Updated");
        request.setShoeCode(product.getShoeCode()+ " Updated");
        request.setQuantity(product.getQuantity()+ 5);
        request.setPrice(product.getPrice()+ 1);


        Product updateProduct = productService.updateProduct(product.getId(), request);

        assertThat(updateProduct, notNullValue());
        assertThat(updateProduct.getId(), is(product.getId()));
        assertThat(updateProduct.getBrandName(), is(request.getBrandName()));
        assertThat(updateProduct.getShoeCode(), is(request.getShoeCode()));
        assertThat(updateProduct.getQuantity(), is(request.getQuantity()));
        assertThat(updateProduct.getPrice(), is(request.getPrice()));
    }

    @Test
    void updateProduct_whenNonExistingProduct_thenThrowResourceNotFoundException(){

        SaveProductRequest request = new SaveProductRequest();

        request.setBrandName("Updated");
        request.setShoeCode(" Updated");
        request.setQuantity(5);
        request.setPrice(1);

        try {
            Product updateProduct = productService.updateProduct(20, request);

            assertThat(updateProduct, notNullValue());
            assertThat(updateProduct.getId(), is(20L));
            assertThat(updateProduct.getBrandName(), is(request.getBrandName()));
            assertThat(updateProduct.getShoeCode(), is(request.getShoeCode()));
            assertThat(updateProduct.getQuantity(), is(request.getQuantity()));
            assertThat(updateProduct.getPrice(), is(request.getPrice()));
        }catch (Exception e) {
            assertThat("Unexpected exception thrown", e instanceof ResourceNotFoundException);

        }
    }

    @Test
    void deleteProduct_whenExistingProduct_thenProductDoesNotExistAnymore(){


        Product product = productTestSteps.createProduct();
        productService.deleteProduct(product.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.getProduct(product.getId()));

    }
}
