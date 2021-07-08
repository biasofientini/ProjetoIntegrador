package com.ecommerce.lifeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.lifeshop.model.Cart;
import com.ecommerce.lifeshop.model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	List<Cart> findAllByUserCart(User user);
	
}