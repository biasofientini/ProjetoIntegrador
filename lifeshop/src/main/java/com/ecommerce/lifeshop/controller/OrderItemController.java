package com.ecommerce.lifeshop.controller;

import java.util.List;
import java.util.Optional;

import com.ecommerce.lifeshop.model.OrderItemDTO;
import com.ecommerce.lifeshop.service.OrderItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/orderitem")
public class OrderItemController {

    @Autowired
    private OrderItemService service;
    
    @GetMapping("/u")
    public ResponseEntity<List<OrderItemDTO>> getAllByUser(@RequestHeader("Authorization") String token, @RequestParam("idOrder") Optional<Long> id){
        return service.getAllByUser(token, id);
    }
    
    @GetMapping("/a")
    public ResponseEntity<List<OrderItemDTO>> getAll(@RequestHeader("Authorization") String token){
    	return service.getAll(token);
    }

    @GetMapping("a/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItem(@RequestHeader("Authorization") String token, @PathVariable Long id){
    	return service.getOrderItem(token, id);
    }
    
    @GetMapping("u/{id}")
    public ResponseEntity<OrderItemDTO> getItem(@RequestHeader("Authorization") String token, @PathVariable Long id){
    	return service.getItem(token, id);
    }
    
}