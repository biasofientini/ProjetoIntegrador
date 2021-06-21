package com.ecommerce.lifeshop.model;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_itens")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idItem;

	@NotNull
	private Integer qtdProduto;

	@ManyToOne
	@JoinColumn(name = "produto_id") 
	private Produto produto; 

	@ManyToOne
	@JoinColumn(name = "carrinho_id")
	private Carrinho carrinho;

	// Id
	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	// QtdProduto
	public Integer getQtdProduto() {
		return qtdProduto;
	}

	public void setQtdProduto(Integer qtdProduto) {
		this.qtdProduto = qtdProduto;
	}

	// Produto
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	// Carrinho
	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}
}
