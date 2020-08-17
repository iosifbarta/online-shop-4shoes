package org.fastrackit.onlineshop4shoes;

import org.fastrackit.onlineshop4shoes.exception.ResourceNotFoundException;
import org.fastrackit.onlineshop4shoes.service.ProductService;
import org.fastrackit.onlineshop4shoes.steps.ProductTestSteps;
import org.fastrackit.onlineshop4shoes.transfer.product.GetProductsRequest;
import org.fastrackit.onlineshop4shoes.transfer.product.ProductResponse;
import org.fastrackit.onlineshop4shoes.transfer.product.SaveProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
            ProductResponse product = productService.createProduct(request);

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
            ProductResponse product = productService.createProduct(request);

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
            ProductResponse product = productService.createProduct(request);

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

        ProductResponse product = productTestSteps.createProduct();

        ProductResponse response = productService.getProductResponse(product.getId());

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

    @Test
    void getProducts_whenExistingProducts_thenReturnPageOfProducts(){
        ProductResponse product = productTestSteps.createProduct();

       Page<ProductResponse> productsPage = productService.getProducts(new GetProductsRequest(), PageRequest.of(0, 1000));
       productsPage.getSort().descending();

        assertThat(productsPage, notNullValue());
        assertThat(productsPage.getTotalElements(), is(1L));
        assertThat(productsPage.getContent().get(0).getId(), is(product.getId()));

    }

    @Test
    void updateProduct_whenValidRequest_thenReturnUpdateProduct (){

        ProductResponse product = productTestSteps.createProduct();

        SaveProductRequest request = new SaveProductRequest();

        request.setBrandName(product.getBrandName()+ " Updated");
        request.setShoeCode(product.getShoeCode()+ " Updated");
        request.setQuantity(product.getQuantity()+ 5);
        request.setPrice(product.getPrice()+ 1);


        ProductResponse updateProduct = productService.updateProduct(product.getId(), request);

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
            ProductResponse updateProduct = productService.updateProduct(20, request);

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

        ProductResponse product = productTestSteps.createProduct();
        productService.deleteProduct(product.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.getProduct(product.getId()));

    }
}
