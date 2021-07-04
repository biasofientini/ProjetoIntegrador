package com.ecommerce.lifeshop.model;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemDTO {
	
	public Long id;

	public Integer productQty;

	public Long productId;

	public Long cartId;
	
	public static CartItemDTO convert(CartItem item) {
		CartItemDTO itemdto = new CartItemDTO();
		itemdto.id = item.getId();
		itemdto.cartId = item.getCart().getId();
		itemdto.productId = item.getProduct().getId();
		itemdto.productQty = item.getProductQty();
		return itemdto;
	}
	
	public static List<CartItemDTO> convertList(List<CartItem> carts){
		return carts.stream().map(c -> CartItemDTO.convert(c)).collect(Collectors.toList());
	}
}