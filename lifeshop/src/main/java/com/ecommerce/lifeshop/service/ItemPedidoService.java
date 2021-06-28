package com.ecommerce.lifeshop.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.lifeshop.model.ItemPedido;
import com.ecommerce.lifeshop.model.ItemPedidoDTO;
import com.ecommerce.lifeshop.model.Pedido;
import com.ecommerce.lifeshop.model.Usuario;
import com.ecommerce.lifeshop.repository.ItemPedidoRepository;
import com.ecommerce.lifeshop.repository.PedidoRepository;
import com.ecommerce.lifeshop.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ItemPedidoService {
	
	@Autowired
	private UsuarioRepository repositoryUsuario;

	@Autowired
	private PedidoRepository repositoryPedido;

	@Autowired
	private ItemPedidoRepository repository;

	//pegar todos os itens pedidos de um usuario
	public ResponseEntity<List<ItemPedidoDTO>> getAll(String token, Optional<Long> idPedido){
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		if(usuario.isPresent()){
			if(idPedido.isPresent()){
				Optional<Pedido> pedido = repositoryPedido.findById(idPedido.get());
				if(pedido.isPresent() && pedido.get().getPedidoUsuario().equals(usuario.get())){
					return ResponseEntity.ok().body(ItemPedidoDTO.convertList(repository.findByPedido(pedido.get())));
				} else{
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
				}
			}
			List<Pedido> pedidos = repositoryPedido.findAllByPedidoUsuario(usuario.get());
			return ResponseEntity.ok().body(ItemPedidoDTO.convertList(repository.findByPedidoIn(pedidos)));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	//pegar um unico item
	public ResponseEntity<ItemPedidoDTO> getItem(String token, Long id){
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<ItemPedido> item = repository.findById(id);
		if(usuario.isPresent() && item.isPresent()){
			Pedido pedido = item.get().getPedido();
			if(pedido.getPedidoUsuario().equals(usuario.get())){
				return ResponseEntity.ok().body(ItemPedidoDTO.convert(item.get()));
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
}
