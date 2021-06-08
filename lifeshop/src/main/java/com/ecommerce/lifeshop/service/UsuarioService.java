package com.ecommerce.lifeshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.model.Usuario;
import com.ecommerce.lifeshop.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	public UsuarioRepository repository;

	// trazer todos
	public ResponseEntity<List<Usuario>> findAllUsuario() {
		List<Usuario> usuarios = repository.findAll();
		if (usuarios.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(usuarios);
		}
	}

	// trazer por id
	public ResponseEntity<Usuario> findUsuarioById(Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.status(404).build());
	}

	// trazer usuários por nome
	public ResponseEntity<List<Usuario>> findUsuarioByNome(String nome) {
		List<Usuario> usuarios = repository.findAllByNomeContainingIgnoreCase(nome);
		if (!usuarios.isEmpty()) {
			return ResponseEntity.status(302).body(usuarios);
		} else {
			return ResponseEntity.status(404).build();
		}
	}

	// salvar usuario
	public Usuario save(Usuario usuario) {
		return repository.save(usuario);
	}

	/*
	 * /deletar por id public void delete(Long id) { repository.deleteById(id); }
	 */

	// deletar por id
	public ResponseEntity<Usuario> delete(Long id) {
		Optional<Usuario> usuId = repository.findById(id);
		if (usuId.isEmpty()) {
			return ResponseEntity.status(404).build();
		} else {
			repository.deleteById(id);
			return ResponseEntity.status(200).build();
		}
	}

}
