package com.ecommerce.lifeshop.model;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ecommerce.lifeshop.model.util.Categoria;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "tb_produtos")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Erro nome produto não pode estar em branco")
	@Size(min = 2, max = 45, message = "Erro tamanho nome")
	private String nome;
	
	@NotBlank(message = "Erro descrição produto não pode estar em branco")
	@Size(min = 5, max = 255, message = "Erro tamanho descricao")
	private String descricao;

	@NotNull(message = "Erro preço não deve estar nulo")
	private Float preco;
	
	@NotNull(message = "Erro estoque não deve estar nulo")
	private Integer estoque;
	
	@NotBlank(message = "Imagem não pode estar em branco")
	private String urlImagem;
	 
	 
	private @Enumerated(EnumType.STRING) Categoria categoria;
	
	/*@OneToMany(mappedBy="produtoComprado", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("produtoComprado")
	private List<ItemPedido> itens = new ArrayList<>();*/
	
	
	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("produto")
	private List<ItemCarrinho> venda = new ArrayList<>();
	

	// UrlImagem
	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	// Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// Nome
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	// Descricao
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	// Preco
	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
	}

	// Estoque
	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	// Enum Categoria
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	// Lista itens
	public List<ItemCarrinho> getVenda() {
		return venda;
	}

	public void setVenda(List<ItemCarrinho> venda) {
		this.venda = venda;
	}
}