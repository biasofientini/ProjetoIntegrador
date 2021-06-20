package com.ecommerce.lifeshop.model.util;



public enum Categoria {
	
	ALIMENTOS(1,"Alimentos"), 
	VESTUARIO(2,"Vestuário"), 
	UTENSILIOS(3,"Utensílios"), 
	ACESSORIOS(4,"Acessórios"), 
	BEMESTAR(5,"Bem Estar");
	
	private String nomeCategoria;
	private Integer id;
	
	Categoria (Integer id, String nome){
		this.id = id;
		this.nomeCategoria = nome;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public Integer getId() {
		return id;
	}
	
}


