package com.ecommerce.lifeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ecommerce.lifeshop.model.Product;
import com.ecommerce.lifeshop.model.util.Category;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	public List<Product> findAllByCategory(Category category);
	
	public List<Product> findByNameContainingIgnoreCase(String name);

	public List<Product> findAllByDescriptionContainingIgnoreCase(String description);

}
