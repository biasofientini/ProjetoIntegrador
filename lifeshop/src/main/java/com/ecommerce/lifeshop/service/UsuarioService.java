package com.ecommerce.lifeshop.service;

import java.nio.charset.StandardCharsets;



import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.model.Role;
import com.ecommerce.lifeshop.model.Usuario;
import com.ecommerce.lifeshop.model.UsuarioDTO;
import com.ecommerce.lifeshop.repository.RoleRepository;
import com.ecommerce.lifeshop.repository.UsuarioRepository;

@Service
public class UsuarioService {

	// usuário tem que estar autenticado e só pode consultar seu próprio carrinho ->
	// idor

	@Autowired
	public UsuarioRepository repository;

	@Autowired
	public RoleRepository repositoryR;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	// trazer todos
	public ResponseEntity<List<Usuario>> findAllUsuario(Optional<String> nome) {
		if (nome.isPresent()) {
			return ResponseEntity.ok().body(repository.findAllByNomeContainingIgnoreCase(nome.get()));
		}
		return ResponseEntity.ok().body(repository.findAll());
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

	// cadastro
	public ResponseEntity<UsuarioDTO> postUsuario(Usuario usuario, Long idRole) {
		Optional<Usuario> user = repository.findByEmail(usuario.getEmail());
		Optional<Role> role = repositoryR.findById(idRole);
		if (!user.isPresent() && !role.isEmpty()) {
			usuario.getRoles().add(role.get());
			String senhaEncoder = encoder.encode(usuario.getSenha());
			usuario.setSenha(senhaEncoder);

			return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioDTO.convert(repository.save(usuario)));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	// token exibindo senha -> Pensar em uma forma de encriptografar o token
	public ResponseEntity<UsuarioDTO> LogarUsuario(UsuarioDTO usuariodto) {
		Optional<Usuario> usuario = repository.findByEmail(usuariodto.email);

		if (usuario.isPresent() && usuariodto.senha != null) {
			if (encoder.matches(usuariodto.senha, usuario.get().getSenha())) {
				String auth = usuariodto.email + ":" + usuariodto.senha;

				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
				String authHeader = "Basic " + new String(encodedAuth);

				usuario.get().setToken(authHeader);
				repository.save(usuario.get());

				return ResponseEntity.ok(UsuarioDTO.convert(usuario.get()));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	}

	// atualizar usuario
	public ResponseEntity<UsuarioDTO> updateUsuario(String token, Usuario usuario, Long id) {
		Optional<Usuario> user = repository.findById(id);
		Optional<Usuario> userToken = repository.findByToken(token);
		if (user.isPresent() && !user.get().getSenha().equals(usuario.getSenha())
				&& user.get().equals(userToken.get())) {
			String senhaEncoder = encoder.encode(usuario.getSenha());
			user.get().setSenha(senhaEncoder);
			user.get().setCep(usuario.getCep());
			user.get().setEmail(usuario.getEmail());
			user.get().setEndereco(usuario.getEndereco());
			user.get().setNome(usuario.getNome());
		}
		return ResponseEntity.status(200).body(UsuarioDTO.convert(repository.save(user.get())));

	}

	// deletar por id
	public ResponseEntity<Object> delete(Long id) {
		if (repository.findById(id) != null) {
			repository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}

