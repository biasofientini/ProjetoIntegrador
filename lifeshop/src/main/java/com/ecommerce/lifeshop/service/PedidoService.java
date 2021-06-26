package com.ecommerce.lifeshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.model.ItemPedido;
import com.ecommerce.lifeshop.model.Pedido;
import com.ecommerce.lifeshop.model.Usuario;
import com.ecommerce.lifeshop.repository.PedidoRepository;
import com.ecommerce.lifeshop.repository.UsuarioRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private UsuarioRepository repositoryUsuario;

	//pegar todos os pedidos de um usuario
	public ResponseEntity<List<Pedido>> getAll(String token){
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		if(usuario.isPresent()) {
			return ResponseEntity.ok().body(repository.findAllByPedidoUsuario(usuario.get()));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	//pegar um pedido especifico de um usuario -> idPedido
	public ResponseEntity<Pedido> getPedido(String token, Long id){
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<Pedido> pedido = repository.findById(id);
		if(usuario.isPresent()) {
			if(pedido.isPresent() && pedido.get().getPedidoUsuario().equals(usuario.get())) {
				return ResponseEntity.ok().body(pedido.get());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}	
	
	//criar novo pedido
	public ResponseEntity<Pedido> postPedido(String token){
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		if(usuario.isPresent()) {
			Pedido pedido = new Pedido();
			pedido.setPedidoUsuario(usuario.get());
			return ResponseEntity.ok().body(repository.save(pedido));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	//Pegar itens pedidos de um pedido especifico
	public ResponseEntity<List<ItemPedido>> getItensPedido(String token, Long id){
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<Pedido> pedido = repository.findById(id);
		if(usuario.isPresent()) {
			if(pedido.isPresent() && pedido.get().getPedidoUsuario().equals(usuario.get())) {
				return ResponseEntity.ok().body(pedido.get().getItens());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	//deletar um pedido
	public ResponseEntity<Object> deletePedido(String token, Long id){
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<Pedido> pedido = repository.findById(id);
		if(usuario.isPresent()) {
			if(pedido.isPresent()) {
				if(pedido.get().getPedidoUsuario().equals(usuario.get())) {
					repository.delete(pedido.get());
					return ResponseEntity.ok().build();
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}
