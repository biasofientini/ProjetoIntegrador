package com.ecommerce.lifeshop.service;

import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.model.Cart;
import com.ecommerce.lifeshop.model.CartItem;
import com.ecommerce.lifeshop.model.CartItemDTO;
import com.ecommerce.lifeshop.model.Product;
import com.ecommerce.lifeshop.model.User;
import com.ecommerce.lifeshop.repository.CartRepository;
import com.ecommerce.lifeshop.repository.CartItemRepository;
import com.ecommerce.lifeshop.repository.ProductRepository;
import com.ecommerce.lifeshop.repository.UserRepository;

@Service
public class CartItemService {

	@Autowired
	private CartItemRepository repository;

	@Autowired
	private CartRepository repositoryCart;

	@Autowired
	private UserRepository repositoryUser;

	@Autowired
	private ProductRepository repositoryProduct;
	
	public ResponseEntity<List<CartItemDTO>> getAll(String token, Optional<Long> id) {
		Optional<User> user = repositoryUser.findByToken(token);
		if (user.isPresent()) {
			if (id.isPresent()) {
				Optional<Cart> cart = repositoryCart.findById(id.get());
				if (cart.isPresent() && cart.get().getUserCart().equals(user.get())) {
					return ResponseEntity.ok().body(CartItemDTO.convertList(repository.findByCart(cart.get())));
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
				}
			}
			List<Cart> carts = repositoryCart.findAllByUserCart(user.get());
			return ResponseEntity.ok().body(CartItemDTO.convertList(repository.findByCartIn(carts)));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	public ResponseEntity<CartItemDTO> postItem(String token, CartItemDTO itemdto) {
		Optional<User> user = repositoryUser.findByToken(token);
		Optional<Cart> cart = repositoryCart.findById(itemdto.cartId);
		Optional<Product> product = repositoryProduct.findById(itemdto.productId);
		if (user.isPresent() && cart.isPresent() && product.isPresent()
				&& cart.get().getUserCart().equals(user.get())) {
			CartItem item = new CartItem();
			item.setCart(cart.get());
			item.setProduct(product.get());
			item.setProductQty(itemdto.productQty);
			return ResponseEntity.ok().body(CartItemDTO.convert(repository.save(item)));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	public ResponseEntity<Object> deleteItem(String token, Long id) {
		Optional<User> user = repositoryUser.findByToken(token);
		Optional<CartItem> item = repository.findById(id);
		if (user.isPresent() && item.isPresent()) {
			Cart cart = item.get().getCart();
			if (cart.getUserCart().equals(user.get())) {
				repository.delete(item.get());
				return ResponseEntity.ok().build();
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	public ResponseEntity<CartItemDTO> putItem(String token, Long id, CartItemDTO itemdto) {
		Optional<User> user = repositoryUser.findByToken(token);
		Optional<CartItem> item = repository.findById(id);
		Optional<Product> product = repositoryProduct.findById(itemdto.productId);
		if (user.isPresent() && item.isPresent()) {
			Cart cart = item.get().getCart();
			if (cart.getUserCart().equals(user.get())) {
				item.get().setProduct(product.get());
				item.get().setCart(cart);
				item.get().setProductQty(itemdto.productQty);
				return ResponseEntity.ok().body(CartItemDTO.convert(repository.save(item.get())));
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	public ResponseEntity<CartItemDTO> getItem(String token, Long id) {
		Optional<User> user = repositoryUser.findByToken(token);
		Optional<CartItem> item = repository.findById(id);
		if (user.isPresent() && item.isPresent()) {
			Cart cart = item.get().getCart();
			if (cart.getUserCart().equals(user.get())) {
				return ResponseEntity.ok().body(CartItemDTO.convert(item.get()));
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}