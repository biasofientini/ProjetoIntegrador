package com.ecommerce.lifeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.lifeshop.model.Carrinho;
import com.ecommerce.lifeshop.model.Usuario;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

	List<Carrinho> findAllByCarrinhoUsuario(Usuario usuario);
	
}