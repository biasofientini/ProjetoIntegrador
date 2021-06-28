package com.ecommerce.lifeshop.model;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_carrinho")
public class Carrinho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCarrinho;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@JsonIgnoreProperties("carrinho")
	private Usuario carrinhoUsuario;
	
	@OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("carrinho")
	private List<ItemCarrinho> itens;
	
	public List<ItemCarrinho> getItens() {
		return itens;
	}

	public void setItens(List<ItemCarrinho> itens) {
		this.itens = itens;
	}

	public Long getIdCarrinho() {
		return idCarrinho;
	}

	public void setIdCarrinho(Long idCarrinho) {
		this.idCarrinho = idCarrinho;
	}

	public Usuario getCarrinhoUsuario() {
		return carrinhoUsuario;
	}

	public void setCarrinhoUsuario(Usuario carrinhoUsuario) {
		this.carrinhoUsuario = carrinhoUsuario;
	}

}
