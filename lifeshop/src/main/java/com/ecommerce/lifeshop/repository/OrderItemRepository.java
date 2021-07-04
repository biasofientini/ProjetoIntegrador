package com.ecommerce.lifeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.lifeshop.model.OrderItem;
import com.ecommerce.lifeshop.model.Order;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
	List<OrderItem> findByOrder(Order order);

    List<OrderItem> findByOrderIn(List<Order> orders);
    
}