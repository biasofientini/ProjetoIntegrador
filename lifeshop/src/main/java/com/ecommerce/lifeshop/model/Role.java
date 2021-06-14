package com.ecommerce.lifeshop.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_role")
public class Role implements GrantedAuthority{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@Column(unique = true) //incluindo sempre mais um role repetido na tb_role
	private String role;
	
	
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("roles")
    //private Collection<Usuario> usuarios;
	private Set<Usuario> usuarios = new HashSet<>();
	
	/*
	@OneToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("roles")
    private Collection<Usuario> usuarios;
	*/
	
	
	


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}





	/*
	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}*/

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}


	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.role;
	}
	
}
