package com.ecommerce.lifeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ecommerce.lifeshop.model.Produto;
import com.ecommerce.lifeshop.model.util.Categoria;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	public List<Produto> findByNomeContainingIgnoreCase(String nome);
	public List<Produto> findAllByDescricaoContainingIgnoreCase(String descricao);
	public List<Produto> findAllByCategoria(Categoria categoria);

}
