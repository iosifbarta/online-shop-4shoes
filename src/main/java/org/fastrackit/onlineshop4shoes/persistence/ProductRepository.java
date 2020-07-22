package org.fastrackit.onlineshop4shoes.persistence;

import org.fastrackit.onlineshop4shoes.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
