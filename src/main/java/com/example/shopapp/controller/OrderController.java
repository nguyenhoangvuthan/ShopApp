package com.example.shopapp.controller;


import com.example.shopapp.dto.OrderDTO;
import com.example.shopapp.models.Order;
import com.example.shopapp.response.OrderResponse;
import com.example.shopapp.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Order order = orderService.createOrder(orderDTO);
            return ResponseEntity.ok().body("createOrder Successful: " + order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("id") Long orderId) {
        try {
            Order orderById = orderService.getOrderById(orderId);
            return ResponseEntity.ok().body(orderById);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    //"/{id}" must be same as variable name. If not same, we must add ("id") to on @PathVariable
    public ResponseEntity<?> updateOrder(@Valid @RequestBody OrderDTO orderDTO, @PathVariable("id") Long id) {
        try {
            Order updateOrder = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok().body(updateOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().body("Order deleted: " + id);
    }
}
