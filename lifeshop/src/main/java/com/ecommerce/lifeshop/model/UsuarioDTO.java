package com.ecommerce.lifeshop.model;

import java.util.List;
import java.util.stream.Collectors;

//Data Transfer Object
public class UsuarioDTO {

	public String nome;
	
	public String email;

	public String senha;
	
	public String token;
	
	public static UsuarioDTO convert(Usuario usuario) {
		UsuarioDTO usuariodto = new UsuarioDTO();
		usuariodto.nome = usuario.getNome();
		usuariodto.email = usuario.getEmail();
		usuariodto.senha = usuario.getSenha();
		usuariodto.token = usuario.getToken();
		return usuariodto;
	}
	
	public static List<UsuarioDTO> convertList(List<Usuario> usuarios){
		return usuarios.stream().map(u -> UsuarioDTO.convert(u)).collect(Collectors.toList());
	}

}