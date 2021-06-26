package com.ecommerce.lifeshop.service;

import org.springframework.stereotype.Service;

@Service
public class ItemPedidoService {

	//pegar todos os itens pedidos de um usuario
	
	
	
	
	
	/*public ResponseEntity<List<ItemCarrinhoDTO>> getAll(String token, Optional<Long> idCarrinho) {
	Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
	if (usuario.isPresent()) {
		if (idCarrinho.isPresent()) {
			Optional<Carrinho> carrinho = repositoryCarrinho.findById(idCarrinho.get());
			if (carrinho.isPresent() && carrinho.get().getCarrinhoUsuario().equals(usuario.get())) {
				return ResponseEntity.ok().body(ItemCarrinhoDTO.convertList(repository.findByCarrinho(carrinho.get())));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}
		List<Carrinho> carrinhos = repositoryCarrinho.findAllByCarrinhoUsuario(usuario.get());
		return ResponseEntity.ok().body(ItemCarrinhoDTO.convertList(repository.findByCarrinhoIn(carrinhos)));
	}
	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
}*/
	//adicionar um novo item
	//deletar um item
	//atualizar um item
	//pegar um unico item
	//pegar itens pedidos por produtos -> nome do produto
	//pegar itens pedidos por produtos -> categoria do produto
	
}
