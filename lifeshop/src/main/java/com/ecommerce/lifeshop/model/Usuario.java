package com.ecommerce.lifeshop.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Erro nome não pode estar em branco")
	@Size(min = 3, max = 45, message = "Erro tamanho nome usuário")
	private String nome;

	@NotBlank(message = "Erro email não pode estar em branco")
	@Email(message = "Erro email não válido")
	private String email;

	@NotBlank(message = "Erro senha não pode estar em branco")
	@Size(min = 8, message = "Erro tamanho senha usuário")
	private String senha;

	@NotBlank(message = "Erro endereço não pode estar em branco")
	private String endereco;

	@NotBlank(message = "Erro cep não pode estar em branco")
	@Size(min = 8, max = 8, message = "Erro tamanho cep usuário")
	private String cep;

	@NotNull(message = "Erro telefone não pode estar em branco")
	
	@NotBlank
	@Size(min = 10, max = 11, message = "Erro tamanho telefone usuário")
	private String telefone;
	
	@NotBlank
	@Size(min = 11, max = 11, message = "Erro tamanho telefone usuário")
	private String cpf;
	
	
	private Integer pontuacao = 0;

	private String token;

	/*@OneToMany(mappedBy = "pedidoUsuario", cascade = CascadeType.ALL)
	private List<Pedido> pedido = new ArrayList<>();*/

	@OneToMany(mappedBy = "carrinhoUsuario", cascade = CascadeType.ALL)
	private List<Carrinho> carrinho = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	@JsonIgnoreProperties("usuarios")
	private Set<Role> roles = new HashSet<>();

	// Token
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	// Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// Senha
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	// Endereço
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	// CEP
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	// List Carrinho
	public List<Carrinho> getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(List<Carrinho> carrinho) {
		this.carrinho = carrinho;
	}

	//Telefone
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	//CPF
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	// Pontuacao
	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

	// Set Role
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}