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

import com.ecommerce.lifeshop.model.Usuario;
import com.ecommerce.lifeshop.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository repository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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

    public ResponseEntity<Usuario> CadastroUsuario(Usuario usuario) {
        Optional<Usuario> user = repository.findByEmail(usuario.getEmail());

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            String senhaEncoder = encoder.encode(usuario.getSenha());
            usuario.setSenha(senhaEncoder);

            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(usuario));
        }
    }

    public Optional<UsuarioLogin> LogarUsuario(Optional<UsuarioLogin> usuario) {
        Optional<Usuario> user = repository.findByEmail(usuario.get().getEmail());

        if (user.isPresent()) {
            if (encoder.matches(usuario.get().getSenha(), user.get().getSenha())) {
                String auth = usuario.get().getEmail() + ":" + usuario.get().getSenha();

                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
                String authHeader = "Basic " + new String(encodedAuth);

                usuario.get().setToken(authHeader);
                usuario.get().setNome(user.get().getNome());
                usuario.get().setSenha(user.get().getSenha());

                return usuario;
            }
        }
        return null;
    }

    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    // deletar por id
    public ResponseEntity delete(Long id) {
        if(repository.findById(id) != null){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
