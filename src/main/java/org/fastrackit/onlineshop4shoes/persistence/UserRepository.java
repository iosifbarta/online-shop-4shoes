package org.fastrackit.onlineshop4shoes.persistence;


import org.fastrackit.onlineshop4shoes.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

}
