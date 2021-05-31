package com.ecoomerce.lifeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecoomerce.lifeshop.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	public List<Categoria> findByNomeContainingIgnoreCase(String nome);

}
