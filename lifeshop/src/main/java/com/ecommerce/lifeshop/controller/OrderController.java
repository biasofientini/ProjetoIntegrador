package com.ecommerce.lifeshop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.lifeshop.model.OrderDTO;
import com.ecommerce.lifeshop.model.OrderDTO2;
import com.ecommerce.lifeshop.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService service;
	
	@GetMapping("/u")
	public ResponseEntity<List<OrderDTO>> getAllByUser(@RequestHeader("Authorization") String token){
		return service.getAllByUser(token);
	}

	@GetMapping("u/{id}")
	public ResponseEntity<OrderDTO> getOrder(@RequestHeader("Authorization") String token, @PathVariable Long id){
		return service.getOrder(token, id);
	}
	
	@GetMapping("/a")
	public ResponseEntity<List<OrderDTO>> getAll(@RequestHeader("Authorization") String token){
		return service.getAll(token);
	}
	
	@GetMapping("/a/{id}")
	public ResponseEntity<OrderDTO> getOrderById(@RequestHeader("Authorization") String token, @PathVariable Long id){
		return service.getOrderById(token, id);
	}
	
	@PostMapping
	public ResponseEntity<OrderDTO> postOrder(@RequestHeader("Authorization") String token, @RequestBody OrderDTO2 order){
		return service.postOrder(token, order.cartId);
	}
	
	 @PutMapping("/{id}")
	 public ResponseEntity<OrderDTO> putOrder(@RequestHeader("Authorization") String token, @Valid @RequestBody OrderDTO orderdto, @PathVariable Long id){
	 	return service.updateOrder(token, orderdto, id);
	 }
	 
	 
}
