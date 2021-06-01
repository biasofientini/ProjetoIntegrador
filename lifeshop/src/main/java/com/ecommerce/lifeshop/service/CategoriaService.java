package com.ecommerce.lifeshop.service;

import com.ecommerce.lifeshop.model.Categoria;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;

	
	public Optional<Categoria> findById(Long id) {
		return repository.findById(id);
	}


	public Categoria save(Categoria categoria) {

		return repository.save(categoria);
	}
	 public void delete (Long id) {
		 repository.deleteById(id);
	 }
}