package com.example.shopapp.services;

import com.example.shopapp.dto.OrderDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.OrderStatus;
import com.example.shopapp.models.User;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.response.OrderResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(()
                -> new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId()));

        Order order = new Order();
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(
                mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isAfter(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Order updateOrder(Long OrderId, OrderDTO OrderDTO) throws DataNotFoundException{
        Order order = orderRepository.findById(OrderId).orElseThrow(
                () -> new DataNotFoundException("Cannot find order with id: " + OrderId));
        User user = userRepository.findById(OrderDTO.getUserId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find user with id: " + OrderDTO.getUserId())
        );

        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(
                mapper -> mapper.skip(Order::setId));
        modelMapper.map(OrderDTO, order);
        order.setUser(user);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }
}
