package org.fastrackit.onlineshop4shoes.service;

import org.fastrackit.onlineshop4shoes.domain.Product;
import org.fastrackit.onlineshop4shoes.persistence.ProductRepository;
import org.fastrackit.onlineshop4shoes.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
