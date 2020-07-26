package org.fastrackit.onlineshop4shoes.service;

import org.fastrackit.onlineshop4shoes.domain.Product;
import org.fastrackit.onlineshop4shoes.exception.ResourceNotFoundException;
import org.fastrackit.onlineshop4shoes.persistence.ProductRepository;
import org.fastrackit.onlineshop4shoes.transfer.GetProductsRequest;
import org.fastrackit.onlineshop4shoes.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);


    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct (SaveProductRequest request){
        LOGGER.info("Creating product {}", request);
        Product product = new Product();
        product.setBrandName(request.getBrandName());
        product.setSize(request.getSize());
        product.setShoeCode(request.getShoeCode());
        product.setGender(request.getGender());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());

        return productRepository.save(product);
    }


    public Product getProduct(long id){

        LOGGER.info("Retrieving product {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found."));
    }

    public Page<Product> getProducts (GetProductsRequest request, Pageable pageable) {

        return productRepository.findByOptionalCriteria(request.getPartialName(),request.getMinimumQuantity(),request.getSize(),
                request.getGender(),request.getMinimumQuantity(),pageable);

    }

    public Product updateProduct(long id, SaveProductRequest request){

        LOGGER.info("Updating product {}: {} ", id, request );

        Product product = getProduct(id);

        BeanUtils.copyProperties(request, product);
        return productRepository.save(product);
    }

    public void deleteProduct(long id){

        LOGGER.info("Deleting product {} ", id);
        productRepository.deleteById(id);
    }

}
