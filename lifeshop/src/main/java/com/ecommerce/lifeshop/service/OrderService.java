package com.ecommerce.lifeshop.service;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
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
import com.ecommerce.lifeshop.model.Product;
import com.ecommerce.lifeshop.model.User;
import com.ecommerce.lifeshop.repository.CartItemRepository;
import com.ecommerce.lifeshop.repository.CartRepository;
import com.ecommerce.lifeshop.repository.OrderItemRepository;
import com.ecommerce.lifeshop.repository.OrderRepository;
import com.ecommerce.lifeshop.repository.ProductRepository;
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

	@Autowired
	private ProductRepository repositoryProduct;

	public ResponseEntity<List<OrderDTO>> getAllByUser(String token) {
		Optional<User> user = repositoryUser.findByToken(token);
		if (user.isPresent()) {
			return ResponseEntity.ok().body(OrderDTO.convertList(repository.findAllByUserOrder(user.get())));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	public ResponseEntity<List<OrderDTO>> getAll(String token){
		Optional<User> user = repositoryUser.findByToken(token);
		if(user.isPresent()) {
			return ResponseEntity.ok().body(OrderDTO.convertList(repository.findAll()));
		} 
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	public ResponseEntity<OrderDTO> getOrderById(String token, Long id){
		Optional<Order> order = repository.findById(id);
		Optional<User> user = repositoryUser.findByToken(token);
		if(order.isPresent() && user.isPresent()){
			return ResponseEntity.ok().body(OrderDTO.convert(order.get()));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	public ResponseEntity<OrderDTO> getOrder(String token, Long id) {
		Optional<User> user = repositoryUser.findByToken(token);
		Optional<Order> order = repository.findById(id);
		if (user.isPresent()) {
			if (order.isPresent() && order.get().getUserOrder().equals(user.get())) {
				return ResponseEntity.ok().body(OrderDTO.convert(order.get()));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	public ResponseEntity<OrderDTO> postOrder(String token, Long idCart) {
		Optional<User> user = repositoryUser.findByToken(token);
		Optional<Cart> cart = repositoryCart.findById(idCart);
		if (user.isPresent() && cart.isPresent() && cart.get().getUserCart().equals(user.get())) {
			Order order = new Order();
			order.setUserOrder(user.get());
			order.setStatus("Em separação");
			repository.save(order);
			List<CartItem> cartitems = repositoryCartItem.findByCart(cart.get());
			HashMap<Long, Integer> counterProducts = groupByProduct(cartitems);

			if(checkStockProducts(counterProducts)){
				Float finalPrice = 0.0f;
				for(Map.Entry<Long, Integer> e: counterProducts.entrySet()){
					Long productId = e.getKey();
					Integer productQty = e.getValue();
					Optional<Product> product = repositoryProduct.findById(productId);
					OrderItem orderitem = new OrderItem();
					orderitem.setOrder(order);
					orderitem.setQuantity(productQty);

					orderitem.setPurchasedProduct(product.get());
					product.get().setStock(product.get().getStock() - productQty);

					orderitem.setUnitPrice(product.get().getPrice());
					finalPrice += productQty * product.get().getPrice();

					repositoryOrderItem.save(orderitem);
				}
				Float discount = Math.min(finalPrice, user.get().getPoints()/10.0f);
				int usedPoints = discount.intValue() * 10;
				finalPrice -= discount;
				user.get().setPoints(user.get().getPoints() + finalPrice.intValue() - usedPoints);
				OrderDTO orderdto = OrderDTO.convert(repository.save(order));
				orderdto.finalPrice = finalPrice;
				return ResponseEntity.ok().body(orderdto);
			}
			else{
				return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	public ResponseEntity<OrderDTO> updateOrder(String token, OrderDTO orderdto, Long id) {
		Optional<User> user = repositoryUser.findByToken(token);
		if (user.isPresent()) {
			Optional<Order> order = repository.findById(id);
			order.get().setStatus(orderdto.status);

			return ResponseEntity.status(HttpStatus.CREATED).body(OrderDTO.convert(repository.save(order.get())));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	private HashMap<Long, Integer> groupByProduct(List<CartItem> cartitems){
		HashMap<Long, Integer> counter = new HashMap<>();
		for(CartItem cartItem: cartitems){
			Integer qty = cartItem.getProductQty();
			Long productId = cartItem.getProduct().getId();
			Integer newQty = counter.getOrDefault(productId, 0) + qty;
			counter.put(productId, newQty);
		}
		return counter;
	}

	private boolean checkStockProducts(HashMap<Long, Integer> counterProducts){
		for(Map.Entry<Long, Integer> e: counterProducts.entrySet()){
			Long productId = e.getKey();
			Optional<Product> product = repositoryProduct.findById(productId);
			boolean canBuy = product.isPresent() && product.get().getStock() >= e.getValue();
			if(!canBuy){
				return false;
			}
		}
		return true;
	}
}
