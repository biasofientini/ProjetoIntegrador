package com.ecommerce.lifeshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.model.Cart;
import com.ecommerce.lifeshop.model.CartItem;
import com.ecommerce.lifeshop.model.Order;
import com.ecommerce.lifeshop.model.OrderDTO;
import com.ecommerce.lifeshop.model.OrderItem;
import com.ecommerce.lifeshop.model.User;
import com.ecommerce.lifeshop.repository.CartItemRepository;
import com.ecommerce.lifeshop.repository.CartRepository;
import com.ecommerce.lifeshop.repository.OrderItemRepository;
import com.ecommerce.lifeshop.repository.OrderRepository;
import com.ecommerce.lifeshop.repository.UserRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private UserRepository repositoryUser;
	
	@Autowired
	private CartRepository repositoryCart;
	
	@Autowired
	private CartItemRepository repositoryCartItem;
	
	@Autowired
	private OrderItemRepository repositoryOrderItem;

	public ResponseEntity<List<OrderDTO>> getAll(String token){
		Optional<User> user = repositoryUser.findByToken(token);
		if(user.isPresent()) {
			return ResponseEntity.ok().body(OrderDTO.convertList(repository.findAllByUserOrder(user.get())));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	public ResponseEntity<OrderDTO> getOrder(String token, Long id){
		Optional<User> user = repositoryUser.findByToken(token);
		Optional<Order> order = repository.findById(id);
		if(user.isPresent()) {
			if(order.isPresent() && order.get().getUserOrder().equals(user.get())) {
				return ResponseEntity.ok().body(OrderDTO.convert(order.get()));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}	
	
	public ResponseEntity<OrderDTO> postOrder(String token, Long idCart){
		Optional<User> user = repositoryUser.findByToken(token);
		Optional<Cart> cart = repositoryCart.findById(idCart);
		if(user.isPresent() && cart.isPresent() && cart.get().getUserCart().equals(user.get())) {
			Order order = new Order();
			order.setUserOrder(user.get());
			repository.save(order);
			List<CartItem> cartitems = repositoryCartItem.findByCart(cart.get());
			Float finalPrice = 0.0f;
			for(int i=0; i<cartitems.size(); i++) {
				OrderItem orderitem = new OrderItem();
				orderitem.setOrder(order);
				orderitem.setQuantity(cartitems.get(i).getProductQty());
				orderitem.setPurchasedProduct(cartitems.get(i).getProduct());
				orderitem.setUnitPrice(cartitems.get(i).getProduct().getPrice());
				finalPrice += orderitem.getUnitPrice() * orderitem.getQuantity();
				repositoryOrderItem.save(orderitem);
			}
			OrderDTO orderdto = OrderDTO.convert(repository.save(order));
			orderdto.finalPrice = finalPrice;
			return ResponseEntity.ok().body(orderdto);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}
