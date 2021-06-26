package com.ecommerce.lifeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.lifeshop.model.Pedido;
import com.ecommerce.lifeshop.model.Usuario;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	List<Pedido> findAllByPedidoUsuario(Usuario usuario);
}
