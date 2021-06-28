package com.ecommerce.lifeshop.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.lifeshop.service.ProdutoService;
import com.ecommerce.lifeshop.model.ProdutoDTO;
import com.ecommerce.lifeshop.model.util.Categoria;

import javax.validation.Valid;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@GetMapping("/categoria/{categoria}")
	public ResponseEntity<List<ProdutoDTO>> getProdutosByCategoria(@PathVariable(value="categoria") Categoria categoria){
		return service.findProdutoByCategoria(categoria);
	}
	
	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> getAllProduct(@RequestParam("descricao") Optional<String> descricao) {
		return service.findAllProduct(descricao);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id) {
		return service.findProdutoById(id);
	}

	@PostMapping
	public ResponseEntity<ProdutoDTO> postProduct(@RequestHeader("Authorization") String token,
			@Valid @RequestBody ProdutoDTO produtodto) {
		return service.postProduto(token, produtodto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProdutoDTO> putProduct(@RequestHeader("Authorization") String token,
			@Valid @RequestBody ProdutoDTO produtodto, @PathVariable Long id) {
		return service.updateProduto(token, produtodto, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProduct(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		return service.delete(token, id);
	}
}