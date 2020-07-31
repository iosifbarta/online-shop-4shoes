package org.fastrackit.onlineshop4shoes.service;


import org.fastrackit.onlineshop4shoes.domain.Product;
import org.fastrackit.onlineshop4shoes.exception.ResourceNotFoundException;
import org.fastrackit.onlineshop4shoes.persistence.ProductRepository;
import org.fastrackit.onlineshop4shoes.transfer.product.GetProductsRequest;
import org.fastrackit.onlineshop4shoes.transfer.product.ProductResponse;
import org.fastrackit.onlineshop4shoes.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    public ProductResponse createProduct (SaveProductRequest request){
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

        Product saveProduct = productRepository.save(product);
        return mapProductResponse(saveProduct);

    }

    public ProductResponse getProductResponse(long id){


        LOGGER.info("Retrieving product {}", id);
        Product product = getProduct(id);

        return mapProductResponse(product);

    }

    public Product getProduct(long id) {
        return productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found."));
    }

    @Transactional
    public Page<ProductResponse> getProducts (GetProductsRequest request, Pageable pageable) {

        Page<Product> page = productRepository.findByOptionalCriteria(request.getPartialName(), request.getMinimumQuantity(), request.getSize(),
                request.getGender(), request.getMinimumQuantity(), pageable);

        List<ProductResponse>productsDtos = new ArrayList<>();
        for(Product product : page.getContent()){
            ProductResponse productResponse =mapProductResponse(product);
            productsDtos.add(productResponse);
        }
        return new PageImpl<>(productsDtos, pageable, page.getTotalElements());
    }

    public ProductResponse updateProduct(long id, SaveProductRequest request){

        LOGGER.info("Updating product {}: {} ", id, request );

        Product product = getProduct(id);

        BeanUtils.copyProperties(request, product);
        Product saveProduct =  productRepository.save(product);

        return mapProductResponse(saveProduct);

    }

    public void deleteProduct(long id){

        LOGGER.info("Deleting product {} ", id);
        productRepository.deleteById(id);
    }

    private ProductResponse mapProductResponse(Product product){

        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setBrandName(product.getBrandName());
        productResponse.setPrice(product.getPrice());
        productResponse.setGender(product.getGender());
        productResponse.setSize(product.getSize());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setShoeCode(product.getShoeCode());
        productResponse.setDescription(product.getDescription());
        productResponse.setImageUrl(product.getImageUrl());

        return productResponse;
    }
}
