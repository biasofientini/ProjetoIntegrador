package com.ecoomerce.lifeshop.service;

import com.ecoomerce.lifeshop.model.Categoria;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecoomerce.lifeshop.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;

	
	public Optional<Categoria> findById(Long id) {
		return repository.findById(id);
	}
	
}
