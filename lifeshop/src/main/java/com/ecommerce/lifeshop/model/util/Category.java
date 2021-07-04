package com.ecommerce.lifeshop.model.util;

public enum Category {
	
	ALIMENTOS(1,"Alimentos"), 
	VESTUARIO(2,"Vestuário"), 
	UTENSILIOS(3,"Utensílios"), 
	ACESSORIOS(4,"Acessórios"), 
	BEMESTAR(5,"Bem Estar");
	
	private String nomeCategoria;
	private Integer id;
	
	Category (Integer id, String nome){
		this.id = id;
		this.nomeCategoria = nome;
	}
	
	public static Category create(Integer id){
		if(id==1) return Category.ALIMENTOS;
		else if(id==2) return Category.VESTUARIO;
		else if(id==3) return Category.UTENSILIOS;
		else if(id==4) return Category.ACESSORIOS;
		else if(id==5) return Category.BEMESTAR;
		else return null;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public Integer getId() {
		return id;
	}
	
}