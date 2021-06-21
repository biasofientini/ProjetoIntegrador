package com.ecommerce.lifeshop.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.lifeshop.model.Item;
import com.ecommerce.lifeshop.model.ItemDTO;
import com.ecommerce.lifeshop.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService service;

	@GetMapping
	public ResponseEntity<List<Item>> getAll(@RequestHeader("Authorization") String token,
			@RequestParam("idCarrinho") Optional<Long> idCarrinho) {
		return service.getAll(token, idCarrinho);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Item> getItem(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		return service.getItem(token, id);
	}

	@PostMapping
	public ResponseEntity<Item> postItem(@RequestHeader("Authorization") String token,
			@Valid @RequestBody ItemDTO itemdto) {
		return service.postItem(token, itemdto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Item> putItem(@RequestHeader("Authorization") String token, @PathVariable Long id,
			@Valid @RequestBody ItemDTO itemdto) {
		return service.putItem(token, id, itemdto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteItem(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		return service.deleteItem(token, id);
	}

}