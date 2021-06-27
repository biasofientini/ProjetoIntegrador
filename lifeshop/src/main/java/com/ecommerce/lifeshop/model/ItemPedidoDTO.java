package com.ecommerce.lifeshop.model;

import java.util.List;
import java.util.stream.Collectors;

public class ItemPedidoDTO {
    public Long id;
    
    public Integer quantidade;
    
    public Float precoUnitario;

    public Long idProduto;

    public Long idPedido;

    public static ItemPedidoDTO convert(ItemPedido itemPedido){
        ItemPedidoDTO itempedidodto = new ItemPedidoDTO();
        itempedidodto.id = itemPedido.getIdItemPedido();
        itempedidodto.quantidade = itemPedido.getQuantidade();
        itempedidodto.precoUnitario = itemPedido.getPrecoUnitario();
        itempedidodto.idProduto = itemPedido.getProdutoComprado().getId();
        itempedidodto.idPedido = itemPedido.getPedido().getIdPedido();
        return itempedidodto;
    }
    
    public static List<ItemPedidoDTO> convertList(List<ItemPedido> itensPedido){
		return itensPedido.stream().map(i -> ItemPedidoDTO.convert(i)).collect(Collectors.toList());
	}
}
// pera ae, tem uma extensao para vscode legal para isso
// pera uns 3 min para eu achar. ok
// instala a extensao Rest Client ok pronto
// pera
// vai na pasta ali de teste e entra naquele pedido.http