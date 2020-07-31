package org.fastrackit.onlineshop4shoes.web;

import org.fastrackit.onlineshop4shoes.service.ProductService;
import org.fastrackit.onlineshop4shoes.transfer.product.GetProductsRequest;
import org.fastrackit.onlineshop4shoes.transfer.product.ProductResponse;
import org.fastrackit.onlineshop4shoes.transfer.product.SaveProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody SaveProductRequest request){
        ProductResponse product = productService.createProduct(request);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable long id, @Valid@RequestBody SaveProductRequest request){
        ProductResponse product = productService.updateProduct(id, request);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable long id){
        ProductResponse product = productService.getProductResponse(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(@Valid GetProductsRequest request, Pageable pageable){
        Page<ProductResponse> products = productService.getProducts(request, pageable);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id){
        productService.deleteProduct(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
