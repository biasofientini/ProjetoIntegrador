package com.ecommerce.lifeshop.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.lifeshop.model.OrderItem;
import com.ecommerce.lifeshop.model.OrderItemDTO;
import com.ecommerce.lifeshop.model.Order;
import com.ecommerce.lifeshop.model.User;
import com.ecommerce.lifeshop.repository.OrderItemRepository;
import com.ecommerce.lifeshop.repository.OrderRepository;
import com.ecommerce.lifeshop.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {
	
	@Autowired
	private UserRepository repositoryUser;

	@Autowired
	private OrderRepository repositoryOrder;

	@Autowired
	private OrderItemRepository repository;

	public ResponseEntity<List<OrderItemDTO>> getAll(String token, Optional<Long> id){
		Optional<User> user = repositoryUser.findByToken(token);
		if(user.isPresent()){
			if(id.isPresent()){
				Optional<Order> order = repositoryOrder.findById(id.get());
				if(order.isPresent() && order.get().getUserOrder().equals(user.get())){
					return ResponseEntity.ok().body(OrderItemDTO.convertList(repository.findByOrder(order.get())));
				} else{
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
				}
			}
			List<Order> orders = repositoryOrder.findAllByUserOrder(user.get());
			return ResponseEntity.ok().body(OrderItemDTO.convertList(repository.findByOrderIn(orders)));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	public ResponseEntity<OrderItemDTO> getItem(String token, Long id){
		Optional<User> user = repositoryUser.findByToken(token);
		Optional<OrderItem> item = repository.findById(id);
		if(user.isPresent() && item.isPresent()){
			Order order = item.get().getOrder();
			if(order.getUserOrder().equals(user.get())){
				return ResponseEntity.ok().body(OrderItemDTO.convert(item.get()));
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
}
