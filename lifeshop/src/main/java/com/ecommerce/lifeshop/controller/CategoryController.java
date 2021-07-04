package com.ecommerce.lifeshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.lifeshop.model.util.Category;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@GetMapping
	public ResponseEntity<String[]> getAllCategoria() {
		
		String categoriesList[] = new String[5];
		
		for(int i=0;i<5;i++) {
			for (Category cat : Category.values()) {
				if(cat.getId().equals(i+1)) {
					categoriesList[i] = cat.getNomeCategoria();
				}
			}
		}
		return ResponseEntity.ok(categoriesList);

	}

}
