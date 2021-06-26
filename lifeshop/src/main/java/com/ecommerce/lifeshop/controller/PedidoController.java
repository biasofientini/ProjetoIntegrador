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

import com.ecommerce.lifeshop.model.ItemPedido;
import com.ecommerce.lifeshop.model.Pedido;
import com.ecommerce.lifeshop.service.PedidoService;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired
	private PedidoService service;
	
	@GetMapping
	public ResponseEntity<List<Pedido>> getAll(@RequestHeader("Authorization") String token){
		return service.getAll(token);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> getPedido(@RequestHeader("Authorization") String token, @PathVariable Long id){
		return service.getPedido(token, id);
	}
	
	@GetMapping("/item/{id}")
	public ResponseEntity<List<ItemPedido>> getItensPedido(@RequestHeader("Authorization") String token, @PathVariable Long id){
		return service.getItensPedido(token, id);
	}
	
	@PostMapping
	public ResponseEntity<Pedido> postPedido(@RequestHeader("Authorization") String token){
		return service.postPedido(token);
	}
	
	@DeleteMapping
	public ResponseEntity<Object> deletePedido(@RequestHeader("Authorization") String token, @PathVariable Long id){
		return service.deletePedido(token, id);
	}

}
