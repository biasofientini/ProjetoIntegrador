package com.ecommerce.lifeshop.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import com.ecommerce.lifeshop.model.UsuarioLogin;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.model.Role;
import com.ecommerce.lifeshop.model.Usuario;
import com.ecommerce.lifeshop.repository.RoleRepository;
import com.ecommerce.lifeshop.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	public UsuarioRepository repository;
	
	@Autowired
	public RoleRepository repositoryR;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	// trazer todos
	public ResponseEntity<List<Usuario>> findAllUsuario() {

		List<Usuario> usuarios = repository.findAll();
		if (usuarios.isEmpty()) {
			return ResponseEntity.status(404).build();
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
			return ResponseEntity.status(200).body(usuarios);
		} else {
			return ResponseEntity.status(404).build();
		}
	}

	public ResponseEntity<Usuario> newUsuario(Usuario usuario, Long idRole) {
		Optional<Usuario> user = repository.findByEmail(usuario.getEmail());
		Optional<Role> role = repositoryR.findById(idRole);
		if (user.isPresent() || role.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} else {
			usuario.getRoles().add(role.get());
			String senhaEncoder = encoder.encode(usuario.getSenha());
			usuario.setSenha(senhaEncoder);

			return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(usuario));
		}
	}

	public ResponseEntity<UsuarioLogin> LogarUsuario(UsuarioLogin usuario) {
		Optional<Usuario> user = repository.findByEmail(usuario.getEmail());
		String senha = usuario.getSenha();

			if (user.isPresent() && senha!=null ) {
				if (encoder.matches(usuario.getSenha(), user.get().getSenha())) {
					String auth = usuario.getEmail() + ":" + usuario.getSenha();

					byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
					String authHeader = "Basic " + new String(encodedAuth);

					usuario.setToken(authHeader);
					usuario.setNome(user.get().getNome());
					usuario.setSenha(user.get().getSenha());

					return ResponseEntity.ok(usuario);
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		

	}

	public ResponseEntity<Usuario> updateUsuario(Usuario usuario) {
		Optional<Usuario> user = repository.findById(usuario.getId());
		
		if(user.isPresent() && !user.get().getSenha().equals(usuario.getSenha())) {
			String senhaEncoder = encoder.encode(usuario.getSenha());
			usuario.setSenha(senhaEncoder);
		}
		return ResponseEntity.status(200).body(repository.save(usuario));

	}

	// deletar por id
	public ResponseEntity<Object> delete(Long id) {
		if (repository.findById(id) != null) {
			repository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
}
