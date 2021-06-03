package com.ecommerce.lifeshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ecommerce.lifeshop.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	public List<Produto> findByNomeContainingIgnoreCase(String nome);
	public List<Produto> findAllByDescricaoContainingIgnoreCase(String descricao);

}
