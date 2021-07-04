package com.ecommerce.lifeshop.model;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ecommerce.lifeshop.model.util.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Erro nome produto não pode estar em branco")
	@Size(min = 2, max = 45, message = "Erro tamanho nome")
	private String name;
	
	@NotBlank(message = "Erro descrição produto não pode estar em branco")
	@Size(min = 5, max = 255, message = "Erro tamanho descricao")
	private String description;

	@NotNull(message = "Erro preço não deve estar nulo")
	private Float price;
	
	@NotNull(message = "Erro estoque não deve estar nulo")
	private Integer stock;
	
	@NotBlank(message = "Imagem não pode estar em branco")
	private String urlImage;
	 
	private @Enumerated(EnumType.STRING) Category category;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("product")
	private List<CartItem> sale = new ArrayList<>();
	

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<CartItem> getSale() {
		return sale;
	}

	public void setSale(List<CartItem> sale) {
		this.sale = sale;
	}
}