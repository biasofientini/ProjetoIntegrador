package com.ecommerce.lifeshop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.lifeshop.model.Usuario;
import com.ecommerce.lifeshop.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@GetMapping("/todos")
	public ResponseEntity<List<Usuario>> getAllUsuario(){
		return service.findAllUsuario();
	}
	@GetMapping("/id/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id){
		return service.findUsuarioById(id);
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Usuario>> getUsuarioByNome(@PathVariable String nome){
		return service.findUsuarioByNome(nome);
	}
	
	@Valid
	@PostMapping("/salvar")
	public ResponseEntity<Usuario> post(@Valid @RequestBody Usuario usuario){
		return ResponseEntity.status(201).body(service.save(usuario));
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> put(@Valid @RequestBody Usuario usuario){
		return ResponseEntity.status(200).body(service.save(usuario));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Usuario> delete(@Valid @PathVariable Long id) {
		return service.delete(id);
	}
	
}
