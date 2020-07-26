package org.fastrackit.onlineshop4shoes.persistence;

import org.fastrackit.onlineshop4shoes.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

   @Query(value = "SELECT  product FROM  Product  product WHERE "+
           "(:partialName IS null OR product.brandName = :partialName) AND "+
           "(:minimumQuantity IS null OR product.quantity = :minimumQuantity) AND "+
           "(:size IS null OR product.size = :size) AND "+
           "(:gender IS null OR product.gender = :gender) AND"+
           "(:price IS null OR product.price BETWEEN 0 AND :price)")

   Page<Product> findByOptionalCriteria(String partialName, Integer minimumQuantity, Double size, String gender,
                                        Integer price, Pageable pageable);
}
