package com.ecommerce.lifeshop.model;

import java.util.List;
import java.util.stream.Collectors;

public class ItemCarrinhoDTO {
	
	public Long id;

	public Integer qtdProduto;

	public Long idProduto;

	public Long idCarrinho;
	
	public static ItemCarrinhoDTO convert(ItemCarrinho item) {
		ItemCarrinhoDTO itemdto = new ItemCarrinhoDTO();
		itemdto.id = item.getIdItemCarrinho();
		itemdto.idCarrinho = item.getCarrinho().getIdCarrinho();
		itemdto.idProduto = item.getProduto().getId();
		itemdto.qtdProduto = item.getQtdProduto();
		return itemdto;
	}
	
	public static List<ItemCarrinhoDTO> convertList(List<ItemCarrinho> carrinhos){
		return carrinhos.stream().map(c -> ItemCarrinhoDTO.convert(c)).collect(Collectors.toList());
	}

}