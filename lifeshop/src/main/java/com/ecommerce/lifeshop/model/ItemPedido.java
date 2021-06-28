package com.ecommerce.lifeshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_item_pedido")
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idItemPedido;

	@NotNull
	private Integer quantidade;

	@NotNull
	private Float precoUnitario;
	
	@ManyToOne
	@JoinColumn(name = "produto_id") 
	//@JsonIgnoreProperties("item_pedido")
	private Produto produtoComprado;
	
	@ManyToOne
	@JoinColumn(name = "pedido_id")
	//@JsonIgnoreProperties("item_pedido")
	private Pedido pedido;

	
	public Long getIdItemPedido() {
		return idItemPedido;
	}

	public void setIdItemPedido(Long idItemPedido) {
		this.idItemPedido = idItemPedido;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Float getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(Float precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public Produto getProdutoComprado() {
		return produtoComprado;
	}

	public void setProdutoComprado(Produto produtoComprado) {
		this.produtoComprado = produtoComprado;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

}