package com.ecommerce.lifeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.lifeshop.model.Carrinho;
import com.ecommerce.lifeshop.model.ItemCarrinho;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {

	List<ItemCarrinho> findByCarrinhoIn(List<Carrinho> carrinhos);

	List<ItemCarrinho> findByCarrinho(Carrinho carrinho);
	
}
