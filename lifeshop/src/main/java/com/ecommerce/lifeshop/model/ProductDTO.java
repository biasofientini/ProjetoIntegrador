package com.ecommerce.lifeshop.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.ecommerce.lifeshop.model.util.Category;

public class ProductDTO {

	public String name;

	public Float price;

	public String description;

	public Integer stock;

	public String urlImage;
	
	public Long id;
	
	public Integer category;
	
	public static ProductDTO convert(Product product) {
		ProductDTO productdto = new ProductDTO();
		productdto.description = product.getDescription();
		productdto.stock = product.getStock();
		productdto.name = product.getName();
		productdto.price = product.getPrice();
		productdto.urlImage = product.getUrlImage();
		productdto.id = product.getId();
		productdto.category = product.getCategory().getId();
		return productdto;
	}
	
	public static List<ProductDTO> convertList(List<Product> products){
		return products.stream().map(p -> ProductDTO.convert(p)).collect(Collectors.toList());
	}

}