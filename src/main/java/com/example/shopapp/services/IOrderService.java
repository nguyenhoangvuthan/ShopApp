package com.example.shopapp.services;

import com.example.shopapp.dto.OrderDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO OrderDTO) throws DataNotFoundException;

    Order getOrderById(Long id);

    List<Order> getAllOrder();

    Order updateOrder(Long OrderId, OrderDTO OrderDTO) throws DataNotFoundException;

    void deleteOrder(Long id);
}
