package com.ecommerce.lifeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.lifeshop.model.ItemPedido;
import com.ecommerce.lifeshop.model.Pedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    
	List<ItemPedido> findByPedido(Pedido pedido);

    List<ItemPedido> findByPedidoIn(List<Pedido> pedidos);
    
}