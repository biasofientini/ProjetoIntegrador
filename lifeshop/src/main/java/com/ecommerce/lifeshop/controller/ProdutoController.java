package com.ecommerce.lifeshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecommerce.lifeshop.model.Produto;
import com.ecommerce.lifeshop.service.ProdutoService;

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
