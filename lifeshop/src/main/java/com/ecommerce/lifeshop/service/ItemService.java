package com.ecommerce.lifeshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.model.Carrinho;
import com.ecommerce.lifeshop.model.Item;
import com.ecommerce.lifeshop.model.ItemDTO;
import com.ecommerce.lifeshop.model.Produto;
import com.ecommerce.lifeshop.model.Usuario;
import com.ecommerce.lifeshop.repository.CarrinhoRepository;
import com.ecommerce.lifeshop.repository.ItemRepository;
import com.ecommerce.lifeshop.repository.ProdutoRepository;
import com.ecommerce.lifeshop.repository.UsuarioRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository repository;

	@Autowired
	private CarrinhoRepository repositoryCarrinho;

	@Autowired
	private UsuarioRepository repositoryUsuario;

	@Autowired
	private ProdutoRepository repositoryProduto;

	// Pegar todos os itens de um usuário
	public ResponseEntity<List<Item>> getAll(String token, Optional<Long> idCarrinho) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		if (usuario.isPresent()) {
			if (idCarrinho.isPresent()) {
				Optional<Carrinho> carrinho = repositoryCarrinho.findById(idCarrinho.get());
				if (carrinho.isPresent() && carrinho.get().getCarrinhoUsuario().equals(usuario.get())) {
					return ResponseEntity.ok().body(repository.findByCarrinho(carrinho.get()));
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
				}
			}
			List<Carrinho> carrinhos = repositoryCarrinho.findAllByCarrinhoUsuario(usuario.get());
			return ResponseEntity.ok().body(repository.findByCarrinhoIn(carrinhos));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	// adicionar item
	public ResponseEntity<Item> postItem(String token, ItemDTO itemdto) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<Carrinho> carrinho = repositoryCarrinho.findById(itemdto.idCarrinho);
		Optional<Produto> produto = repositoryProduto.findById(itemdto.idProduto);
		if (usuario.isPresent() && carrinho.isPresent() && produto.isPresent()
				&& carrinho.get().getCarrinhoUsuario().equals(usuario.get())) {
			Item item = new Item();
			item.setCarrinho(carrinho.get());
			item.setProduto(produto.get());
			item.setQtdProduto(itemdto.qtdProduto);
			return ResponseEntity.ok().body(repository.save(item));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	// Deletar item
	public ResponseEntity<Object> deleteItem(String token, Long id) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<Item> item = repository.findById(id);
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
	public ResponseEntity<Item> putItem(String token, Long id, ItemDTO itemdto) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<Item> item = repository.findById(id);
		Optional<Produto> produto = repositoryProduto.findById(itemdto.idProduto);
		if (usuario.isPresent() && item.isPresent()) {
			Carrinho carrinho = item.get().getCarrinho();
			if (carrinho.getCarrinhoUsuario().equals(usuario.get())) {
				item.get().setProduto(produto.get());
				item.get().setCarrinho(carrinho);
				item.get().setQtdProduto(itemdto.qtdProduto);
				return ResponseEntity.ok().body(repository.save(item.get()));
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	// Pegar um único item
	public ResponseEntity<Item> getItem(String token, Long id) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		Optional<Item> item = repository.findById(id);
		if (usuario.isPresent() && item.isPresent()) {
			Carrinho carrinho = item.get().getCarrinho();
			if (carrinho.getCarrinhoUsuario().equals(usuario.get())) {
				return ResponseEntity.ok().body(item.get());
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}