package com.ecommerce.lifeshop.model;

import java.util.List;
import java.util.stream.Collectors;

public class CartDTO {
	
	public Long id;
	
	public static CartDTO convert(Cart cart) {
		CartDTO cartdto = new CartDTO();
		cartdto.id = cart.getId();
		return cartdto;
	}
	
	public static List<CartDTO> convertList(List<Cart> carts){
		return carts.stream().map(c -> CartDTO.convert(c)).collect(Collectors.toList());
	}
	
}