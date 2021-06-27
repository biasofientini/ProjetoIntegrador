package com.ecommerce.lifeshop.service;

import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.model.Carrinho;
import com.ecommerce.lifeshop.model.ItemCarrinho;
import com.ecommerce.lifeshop.model.ItemCarrinhoDTO;
import com.ecommerce.lifeshop.model.Produto;
import com.ecommerce.lifeshop.model.Usuario;
import com.ecommerce.lifeshop.repository.CarrinhoRepository;
import com.ecommerce.lifeshop.repository.ItemCarrinhoRepository;
import com.ecommerce.lifeshop.repository.ProdutoRepository;
import com.ecommerce.lifeshop.repository.UsuarioRepository;

@Service
public class ItemCarrinhoService {

	@Autowired
	private ItemCarrinhoRepository repository;

	@Autowired
	private CarrinhoRepository repositoryCarrinho;

	@Autowired
	private UsuarioRepository repositoryUsuario;

	@Autowired
	private ProdutoRepository repositoryProduto;
	
	// Pegar todos os itens de um usuário
	public ResponseEntity<List<ItemCarrinhoDTO>> getAll(String token, Optional<Long> idCarrinho) {
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
	}

	// adicionar item
	public ResponseEntity<ItemCarrinhoDTO> postItem(String token, ItemCarrinhoDTO itemdto) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<Carrinho> carrinho = repositoryCarrinho.findById(itemdto.idCarrinho);
		Optional<Produto> produto = repositoryProduto.findById(itemdto.idProduto);
		if (usuario.isPresent() && carrinho.isPresent() && produto.isPresent()
				&& carrinho.get().getCarrinhoUsuario().equals(usuario.get())) {
			ItemCarrinho item = new ItemCarrinho();
			item.setCarrinho(carrinho.get());
			item.setProduto(produto.get());
			item.setQtdProduto(itemdto.qtdProduto);
			return ResponseEntity.ok().body(ItemCarrinhoDTO.convert(repository.save(item)));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	// Deletar item
	public ResponseEntity<Object> deleteItem(String token, Long id) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<ItemCarrinho> item = repository.findById(id);
		if (usuario.isPresent() && item.isPresent()) {
			Carrinho carrinho = item.get().getCarrinho();
			if (carrinho.getCarrinhoUsuario().equals(usuario.get())) {
				repository.delete(item.get());
				return ResponseEntity.ok().build();
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	// Atualizar item
	public ResponseEntity<ItemCarrinhoDTO> putItem(String token, Long id, ItemCarrinhoDTO itemdto) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<ItemCarrinho> item = repository.findById(id);
		Optional<Produto> produto = repositoryProduto.findById(itemdto.idProduto);
		if (usuario.isPresent() && item.isPresent()) {
			Carrinho carrinho = item.get().getCarrinho();
			if (carrinho.getCarrinhoUsuario().equals(usuario.get())) {
				item.get().setProduto(produto.get());
				item.get().setCarrinho(carrinho);
				item.get().setQtdProduto(itemdto.qtdProduto);
				return ResponseEntity.ok().body(ItemCarrinhoDTO.convert(repository.save(item.get())));
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	// Pegar um único item
	public ResponseEntity<ItemCarrinhoDTO> getItem(String token, Long id) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<ItemCarrinho> item = repository.findById(id);
		if (usuario.isPresent() && item.isPresent()) {
			Carrinho carrinho = item.get().getCarrinho();
			if (carrinho.getCarrinhoUsuario().equals(usuario.get())) {
				return ResponseEntity.ok().body(ItemCarrinhoDTO.convert(item.get()));
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}