package com.ecommerce.lifeshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.lifeshop.model.Categoria;
import com.ecommerce.lifeshop.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService service;

	@GetMapping("/id/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable Long id) {
		Optional<Categoria> opt = service.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(opt.get());
	}
	@PostMapping ("/salvar")
	public ResponseEntity<Categoria> post(@RequestBody Categoria categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(categoria));

	}
	@PutMapping("/atualizar")
	public ResponseEntity<Categoria> put(@RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.OK).body(service.save(categoria));
	}
	@DeleteMapping ("/delete/{id}")
	public void delete(@PathVariable Long id ) {
	service.delete(id);
	}

}