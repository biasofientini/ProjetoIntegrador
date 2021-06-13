package com.ecommerce.lifeshop.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "tb_carrinho")
public class Carrinho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCarrinho;
	
	@NotNull
	@NotBlank
	private Float total;
	
	@NotNull
	@NotBlank
	private Date data;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id") //usuario_id é uma FK da tb_usuario dentro da tb_carrinho (coluna falsa só para vc a ligação)
	private Usuario carrinhoUsuario;
	
	
	@OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("{carrinho}")
	private List<Item> vendas;

	public Long getIdCarrinho() {
		return idCarrinho;
	}

	public void setIdCarrinho(Long idCarrinho) {
		this.idCarrinho = idCarrinho;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	/*
	public Usuario getCarrinhoUsuario() {
		return carrinhoUsuario;
	}

	public void setCarrinhoUsuario(Usuario carrinhoUsuario) {
		this.carrinhoUsuario = carrinhoUsuario;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}*/

}
