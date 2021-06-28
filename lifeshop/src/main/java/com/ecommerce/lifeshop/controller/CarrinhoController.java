package com.ecommerce.lifeshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.lifeshop.model.Carrinho;
import com.ecommerce.lifeshop.model.ItemCarrinho;
import com.ecommerce.lifeshop.service.CarrinhoService;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

	@Autowired
	private CarrinhoService service;

	@GetMapping
	public ResponseEntity<List<Carrinho>> findAll(@RequestHeader("Authorization") String token) {
		return service.findAll(token);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Carrinho> getCart(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		return service.getCart(token, id);
	}
	
	@GetMapping("/item/{id}")
	public ResponseEntity<List<ItemCarrinho>> getItensCarrinho(@RequestHeader("Authorization") String token, @PathVariable Long id){
		return service.getItensCarrinho(token, id);
	}

	@PostMapping
	public ResponseEntity<Carrinho> postCart(@RequestHeader("Authorization") String token) {
		return service.postCart(token);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCart(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		return service.deleteCart(token, id);
	}

}