package com.ecommerce.lifeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.lifeshop.model.Order;
import com.ecommerce.lifeshop.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	List<Order> findAllByUserOrder(User user);
}
