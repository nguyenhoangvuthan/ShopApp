package com.example.shopapp.controller;

import com.example.shopapp.dto.OrderDetailDTO;
import com.example.shopapp.models.OrderDetails;
import com.example.shopapp.services.IOrderDetailService;
import com.example.shopapp.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
public class OrderDetailController {
    protected final IOrderDetailService orderDetailService;

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetails orderDetails = orderDetailService.createOrderDetails(orderDetailDTO);
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable Long id) {
        OrderDetails orderDetail = null;
        try {
            orderDetail = orderDetailService.getOrderDetail(id);
        } catch (com.example.shopapp.exceptions.DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("getOrderDetail" + orderDetail);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable Long orderId) {
        try{
            List<OrderDetails> orderDetails = orderDetailService.findByOrderId(orderId);
            return ResponseEntity.ok("getListOrderDetail" + orderDetails);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO,
                                               @PathVariable Long id) {
        return ResponseEntity.ok(orderDetailDTO + "updateOrderDetail" + id);
    }
}
