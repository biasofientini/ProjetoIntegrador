package com.ecommerce.lifeshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.lifeshop.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	public List<Categoria> findByNomeContainingIgnoreCase(String nome);
	public List<Categoria> findAllByDescricaoContainingIgnoreCase(String descricao);

}