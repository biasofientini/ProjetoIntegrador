package com.ecommerce.lifeshop.controller;

import java.util.List;
import java.util.Optional;

import com.ecommerce.lifeshop.model.ItemPedidoDTO;
import com.ecommerce.lifeshop.service.ItemPedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/itemp")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService service;
    
    @GetMapping
    public ResponseEntity<List<ItemPedidoDTO>> getAll(@RequestHeader("Authorization") String token, @RequestParam("idPedido") Optional<Long> idPedido){
        return service.getAll(token, idPedido);
    }
}