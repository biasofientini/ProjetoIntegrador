package com.ecommerce.lifeshop.model;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemDTO {
    public Long id;
    
    public Integer quantity;
    
    public Float unitPrice;

    public Long productId;

    public Long orderId;

    public static OrderItemDTO convert(OrderItem orderItem){
        OrderItemDTO orderitemdto = new OrderItemDTO();
        orderitemdto.id = orderItem.getId();
        orderitemdto.quantity = orderItem.getQuantity();
        orderitemdto.unitPrice = orderItem.getUnitPrice();
        orderitemdto.productId = orderItem.getPurchasedProduct().getId();
        orderitemdto.orderId = orderItem.getOrder().getId();
        return orderitemdto;
    }
    
    public static List<OrderItemDTO> convertList(List<OrderItem> orderItems){
		return orderItems.stream().map(i -> OrderItemDTO.convert(i)).collect(Collectors.toList());
	}
}