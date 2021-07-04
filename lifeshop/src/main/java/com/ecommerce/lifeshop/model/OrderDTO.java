package com.ecommerce.lifeshop.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO {

    public Long id;
    
    public Long userId;
    
    public Date date;

    public static OrderDTO convert(Order order) {
        OrderDTO orderdto = new OrderDTO();
        orderdto.id = order.getId();
        orderdto.userId = order.getUserOrder().getId();
        orderdto.date = order.getDate();
        return orderdto;
    }

    public static List<OrderDTO> convertList(List<Order> orders) {
        return orders.stream().map(p -> OrderDTO.convert(p)).collect(Collectors.toList());
    }
}