package com.ecommerce.lifeshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.model.Order;
import com.ecommerce.lifeshop.model.OrderDTO;
import com.ecommerce.lifeshop.model.User;
import com.ecommerce.lifeshop.repository.OrderRepository;
import com.ecommerce.lifeshop.repository.UserRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private UserRepository repositoryUser;

	public ResponseEntity<List<OrderDTO>> getAll(String token){
		Optional<User> user = repositoryUser.findByToken(token);
		if(user.isPresent()) {
			return ResponseEntity.ok().body(OrderDTO.convertList(repository.findAllByUserOrder(user.get())));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	public ResponseEntity<OrderDTO> getOrder(String token, Long id){
		Optional<User> user = repositoryUser.findByToken(token);
		Optional<Order> order = repository.findById(id);
		if(user.isPresent()) {
			if(order.isPresent() && order.get().getUserOrder().equals(user.get())) {
				return ResponseEntity.ok().body(OrderDTO.convert(order.get()));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}	
	
	public ResponseEntity<OrderDTO> postOrder(String token){
		Optional<User> user = repositoryUser.findByToken(token);
		if(user.isPresent()) {
			Order order = new Order();
			order.setUserOrder(user.get());
			return ResponseEntity.ok().body(OrderDTO.convert(repository.save(order)));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	public ResponseEntity<Object> deleteOrder(String token, Long id){
		Optional<User> user = repositoryUser.findByToken(token);
		Optional<Order> order = repository.findById(id);
		if(user.isPresent()) {
			if(order.isPresent()) {
				if(order.get().getUserOrder().equals(user.get())) {
					repository.delete(order.get());
					return ResponseEntity.ok().build();
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}
