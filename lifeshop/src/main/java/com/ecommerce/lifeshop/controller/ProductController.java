package com.ecommerce.lifeshop.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.lifeshop.service.ProductService;
import com.ecommerce.lifeshop.model.ProductDTO;
import com.ecommerce.lifeshop.model.util.Category;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping("/category/{category}")
	public ResponseEntity<List<ProductDTO>> getProductsByCategories(@PathVariable(value="category") Category category){
		return service.findProductByCategory(category);
	}
	
	@GetMapping
	public ResponseEntity<List<ProductDTO>> getAllProducts(@RequestParam("description") Optional<String> description) {
		return service.findAllProduct(description);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		return service.findProductById(id);
	}

	@PostMapping
	public ResponseEntity<ProductDTO> postProduct(@RequestHeader("Authorization") String token,
			@Valid @RequestBody ProductDTO productdto) {
		return service.postProduct(token, productdto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> putProduct(@RequestHeader("Authorization") String token,
			@Valid @RequestBody ProductDTO productdto, @PathVariable Long id) {
		return service.updateProduct(token, productdto, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProduct(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		return service.deleteProduct(token, id);
	}
}