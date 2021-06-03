package com.ecommerce.lifeshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.lifeshop.service.ProdutoService;
import com.ecommerce.lifeshop.model.Produto;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Produto> findById(@PathVariable Long id) {
		return service.findProdutoById(id);
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Produto>> getProdutoByDescricao(@PathVariable String descricao){
		return service.findProdutoByDescricao(descricao);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Produto> post(@RequestBody Produto produto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(produto));
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Produto> put(@RequestBody Produto produto) {
		return ResponseEntity.status(HttpStatus.OK).body(service.save(produto));
	}
	
	@DeleteMapping ("/delete/{id}")
	public void delete(@PathVariable Long id ) {
	service.delete(id);
	}
	

}
