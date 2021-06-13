package com.ecommerce.lifeshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_itens")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idItem;

	@NotNull
	@NotBlank
	private Integer qdtProduto;
	
	@NotNull
	@NotBlank
	private Float totalItem;
	
	
	@ManyToOne
	@JoinColumn(name = "produto_id") //em dúvida se farei lista, mas acho que devo listar dentro
									//do carrinho uma lista de vendas
	private Produto produto; //cada venda representa um único produto (é uma linha da nota fiscal)

	
	@ManyToOne //muitas vendas em um único carrinho
	@JoinColumn(name = "carrinho_id")
	private Carrinho carrinho;

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public Integer getQdtProduto() {
		return qdtProduto;
	}

	public void setQdtProduto(Integer qdtProduto) {
		this.qdtProduto = qdtProduto;
	}

	public Float getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(Float totalItem) {
		this.totalItem = totalItem;
	}
/*
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	} 

*/
}
