package com.ecommerce.lifeshop.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.model.Carrinho;
import com.ecommerce.lifeshop.model.ItemCarrinho;
import com.ecommerce.lifeshop.model.Usuario;
import com.ecommerce.lifeshop.repository.CarrinhoRepository;
import com.ecommerce.lifeshop.repository.UsuarioRepository;

@Service
public class CarrinhoService {

	@Autowired
	private CarrinhoRepository repository;

	@Autowired
	private UsuarioRepository repositoryUsuario;

	// todos os carrinhos de um usuário
	public ResponseEntity<List<Carrinho>> findAll(String token) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		if (usuario.isPresent()) {
			return ResponseEntity.ok().body(repository.findAllByCarrinhoUsuario(usuario.get()));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	// novo carrinho
	public ResponseEntity<Carrinho> postCart(String token) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		if (usuario.isPresent()) {
			Carrinho carrinho = new Carrinho();
			carrinho.setCarrinhoUsuario(usuario.get());
			return ResponseEntity.ok().body(repository.save(carrinho));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	// pegar um carrinho de um usuário
	public ResponseEntity<Carrinho> getCart(String token, Long id) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<Carrinho> carrinho = repository.findById(id);
		if (usuario.isPresent()) {
			if(carrinho.isPresent() && carrinho.get().getCarrinhoUsuario().equals(usuario.get())) {
				return ResponseEntity.ok().body(carrinho.get());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	//pegar todos os itens de um carrinho 
	public ResponseEntity<List<ItemCarrinho>> getItensCarrinho(String token, Long id){
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<Carrinho> carrinho = repository.findById(id);
		if(usuario.isPresent()) {
			if(carrinho.isPresent() && carrinho.get().getCarrinhoUsuario().equals(usuario.get())) {
				return ResponseEntity.ok().body(carrinho.get().getItens());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	// deletar carrinho por id -> e todos os seus itens
	public ResponseEntity<Object> deleteCart(String token, Long id) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<Carrinho> carrinho = repository.findById(id);
		if (usuario.isPresent()) {
			if (carrinho.isPresent()) {
				if (carrinho.get().getCarrinhoUsuario().equals(usuario.get())) {
					repository.delete(carrinho.get());
					return ResponseEntity.ok().build();
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

}