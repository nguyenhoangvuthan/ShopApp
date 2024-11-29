package com.example.shopapp.services;

import com.example.shopapp.dto.OrderDetailDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.OrderDetails;

import java.util.List;

public interface IOrderDetailService {
    OrderDetails createOrderDetails(OrderDetailDTO orderDetailDTO) throws DataNotFoundException;

    OrderDetails getOrderDetail(Long id) throws DataNotFoundException;

    OrderDetails updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;

    void deleteOrderDetails(Long id);

    List<OrderDetails> findByOrderId(Long orderId);
}
