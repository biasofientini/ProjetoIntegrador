package com.ecommerce.lifeshop.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.lifeshop.model.util.Categoria;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@GetMapping("/todas") //esse método não será um response entity mas sim um retorno String[] para colocar no setlist 
	public ResponseEntity<String[]> getAllCategoria() {
		
		String listaCategorias[] = new String[5];
		
		for(int i=0;i<5;i++) {
			for (Categoria cat : Categoria.values()) {
				if(cat.getId().equals(i+1)) {
					listaCategorias[i] = cat.getNomeCategoria();
				}
			}
		}
		return ResponseEntity.ok(listaCategorias);

	}

}
