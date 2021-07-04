package com.ecommerce.lifeshop.controller;

import java.util.List;
import java.util.Optional;

import com.ecommerce.lifeshop.model.OrderItemDTO;
import com.ecommerce.lifeshop.service.OrderItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/orderitem")
public class OrderItemController {

    @Autowired
    private OrderItemService service;
    
    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> getAll(@RequestHeader("Authorization") String token, @RequestParam("id") Optional<Long> id){
        return service.getAll(token, id);
    }
}