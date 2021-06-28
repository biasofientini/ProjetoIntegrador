package com.ecommerce.lifeshop.model;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoDTO {

	public String nome;

	public Float preco;

	public String descricao;

	public Integer estoque;

	public String urlImagem;
	
	public static ProdutoDTO convert(Produto produto) {
		ProdutoDTO produtodto = new ProdutoDTO();
		produtodto.descricao = produto.getDescricao();
		produtodto.estoque = produto.getEstoque();
		produtodto.nome = produto.getNome();
		produtodto.preco = produto.getPreco();
		produtodto.urlImagem = produto.getUrlImagem();
		return produtodto;
	}
	
	public static List<ProdutoDTO> convertList(List<Produto> produtos){
		return produtos.stream().map(p -> ProdutoDTO.convert(p)).collect(Collectors.toList());
	}

}