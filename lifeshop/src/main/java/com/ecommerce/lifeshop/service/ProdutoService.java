package com.ecommerce.lifeshop.service;

import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ecommerce.lifeshop.model.Produto;
import com.ecommerce.lifeshop.model.ProdutoDTO;
import com.ecommerce.lifeshop.model.Usuario;
import com.ecommerce.lifeshop.model.util.Categoria;
import com.ecommerce.lifeshop.repository.ProdutoRepository;
import com.ecommerce.lifeshop.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private UsuarioRepository repositoryUsuario;

	// trazer todos e se houver descricao, filtra
	public ResponseEntity<List<ProdutoDTO>> findAllProduct(Optional<String> descricao) {
		if (descricao.isPresent()) {
			List<Produto> produtos = repository.findAllByDescricaoContainingIgnoreCase(descricao.get());
			return ResponseEntity.ok().body(ProdutoDTO.convertList(produtos));
		}
		return ResponseEntity.status(200).body(ProdutoDTO.convertList(repository.findAll()));
	}

	// trazer por id
	public ResponseEntity<ProdutoDTO> findProdutoById(Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(ProdutoDTO.convert(resp))).orElse(ResponseEntity.status(404).build());
	}
	
	// trazer por categoria
		public ResponseEntity<List<ProdutoDTO>> findProdutoByCategoria(Categoria categoria) {
			List<Produto> produtos = repository.findAllByCategoria(categoria);
			if (!produtos.isEmpty()) {
				return ResponseEntity.status(200).body(ProdutoDTO.convertList(produtos));
			} else {
				return ResponseEntity.status(404).build();
			}
		}

	// salvar produto
	public ResponseEntity<ProdutoDTO> postProduto(String token, ProdutoDTO produtodto) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		if (usuario.isPresent()) {
			Produto produto = new Produto();
			produto.setNome(produtodto.nome);
			produto.setPreco(produtodto.preco);
			produto.setDescricao(produtodto.descricao);
			produto.setEstoque(produtodto.estoque);
			produto.setUrlImagem(produtodto.urlImagem);

			return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoDTO.convert(repository.save(produto)));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	// atualizar
	public ResponseEntity<ProdutoDTO> updateProduto(String token, ProdutoDTO produtodto, Long id) {
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		if (usuario.isPresent()) {
			Optional<Produto> produto = repository.findById(id);
			produto.get().setNome(produtodto.nome);
			produto.get().setPreco(produtodto.preco);
			produto.get().setDescricao(produtodto.descricao);
			produto.get().setEstoque(produtodto.estoque);
			produto.get().setUrlImagem(produtodto.urlImagem);

			return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoDTO.convert(repository.save(produto.get())));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	// deletar por id
	public ResponseEntity<Object> delete(String token, Long id) {
		Optional<Produto> produto = repository.findById(id);
		Optional<Usuario> usuario = repositoryUsuario.findByToken(token);
		if (usuario.isPresent() && produto.isPresent()) {
			System.out.println("chegou aqui");
			repository.delete(produto.get());
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}
