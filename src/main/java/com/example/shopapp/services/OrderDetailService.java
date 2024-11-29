package com.example.shopapp.services;

import com.example.shopapp.dto.OrderDetailDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.OrderDetails;
import com.example.shopapp.models.Product;
import com.example.shopapp.repositories.OrderDetailRepository;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetails createOrderDetails(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId()).orElseThrow(
                () -> new DataNotFoundException("Order not found"));
        Product product = productRepository.findById(orderDetailDTO.getProductId()).orElseThrow(
                () -> new DataNotFoundException("Product not found"));
        OrderDetails orderDetails = OrderDetails.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProduct())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();
        return orderDetailRepository.save(orderDetails);
    }

    @Override
    public OrderDetails getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Cannot find OrderDetail with id: " + id)
        );
    }

    @Override
    public OrderDetails updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        return orderDetailRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Order not found")
        );
    }

    @Override
    public void deleteOrderDetails(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetails> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
