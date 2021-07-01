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

import com.ecommerce.lifeshop.model.Usuario;
import com.ecommerce.lifeshop.model.UsuarioDTO;
import com.ecommerce.lifeshop.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@GetMapping
	public ResponseEntity<List<Usuario>> getAllUser(@RequestParam("nome") Optional<String> nome) {
		return service.findAllUsuario(nome);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id) {
		return service.findUsuarioById(id);
	}

	@PostMapping("/role/{id_role}")
	public ResponseEntity<UsuarioDTO> postUser(@Valid @RequestBody UsuarioDTO usuario,
			@PathVariable(value = "id_role") Long idRole) {
		System.out.println("chocolate");
		return service.postUsuario(usuario, idRole);
	}

	@PostMapping
	public ResponseEntity<UsuarioDTO> logUser(@Valid @RequestBody UsuarioDTO usuario) {
		return service.LogarUsuario(usuario);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> putUser(@RequestHeader("Authorization") String token, @Valid @RequestBody Usuario usuario, @PathVariable Long id) {
		return service.updateUsuario(token, usuario, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		return service.delete(id);
	}

}