package com.ecommerce.lifeshop.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping
	public ResponseEntity<List<OrderDTO>> getAll(@RequestHeader("Authorization") String token){
		return service.getAll(token);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> getOrder(@RequestHeader("Authorization") String token, @PathVariable Long id){
		return service.getOrder(token, id);
	}
	
	@PostMapping
	public ResponseEntity<OrderDTO> postOrder(@RequestHeader("Authorization") String token, @RequestBody OrderDTO2 order){
		return service.postOrder(token, order.cartId);
	}

}
