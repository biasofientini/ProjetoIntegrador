package com.ecommerce.lifeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.lifeshop.model.Carrinho;
import com.ecommerce.lifeshop.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByCarrinhoIn(List<Carrinho> carrinhos);

	List<Item> findByCarrinho(Carrinho carrinho);

}
