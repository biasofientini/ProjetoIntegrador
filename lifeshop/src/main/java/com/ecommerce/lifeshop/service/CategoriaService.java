package com.ecommerce.lifeshop.service;

import com.ecommerce.lifeshop.model.Categoria;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;

	//trazer por id
	public ResponseEntity<Categoria> findCategoriaById(Long id) {
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(404).build());
	}

	//salvar categoria
	public Categoria save(Categoria categoria) {
		return repository.save(categoria);
	}
	
	//deletar por id
	 public void delete (Long id) {
		 repository.deleteById(id);
	 }
	 
	 //trazer por descricao
	 public ResponseEntity<List<Categoria>> findCategoriaByDescricao(String descricao){
		 Optional<List<Categoria>> categorias = repository.findAllByDescricaoContainingIgnoreCase(descricao);
		 if(!categorias.isEmpty()) {
			 return ResponseEntity.status(302).body(categorias.get());
		 } else {
		 return ResponseEntity.status(204).build();
		 }
	 }
}