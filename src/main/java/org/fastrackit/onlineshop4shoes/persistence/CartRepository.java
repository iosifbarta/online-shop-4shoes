package org.fastrackit.onlineshop4shoes.persistence;

import org.fastrackit.onlineshop4shoes.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
