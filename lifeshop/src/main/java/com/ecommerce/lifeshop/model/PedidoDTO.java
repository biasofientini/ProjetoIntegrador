package com.ecommerce.lifeshop.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoDTO {

    public Long id;
    
    public Long idUsuario;
    
    public Date data;

    public static PedidoDTO convert(Pedido pedido) {
        PedidoDTO pedidodto = new PedidoDTO();
        pedidodto.id = pedido.getIdPedido();
        pedidodto.idUsuario = pedido.getPedidoUsuario().getId();
        pedidodto.data = pedido.getData();
        return pedidodto;
    }

    public static List<PedidoDTO> convertList(List<Pedido> pedidos) {
        return pedidos.stream().map(p -> PedidoDTO.convert(p)).collect(Collectors.toList());
    }
} // agr eu que preciso de ajuda KKKKKKK vai na itempedido repository