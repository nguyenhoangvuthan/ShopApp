package com.example.shopapp.response;

import com.example.shopapp.models.Order;
import com.example.shopapp.models.OrderDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailResponse {
    Long id;

    @JsonProperty("order_id")
    Long orderId;

    @JsonProperty("product_id")
    Long productId;

    @JsonProperty("price")
    Float price;

    @JsonProperty("number_of_products")
    int numberOfProducts;

    @JsonProperty("total_money")
    Float totalMoney;

    String color;

    public static OrderDetailResponse fromOrderDetail(OrderDetails orderDetails) {
        return OrderDetailResponse.builder()
                .id(orderDetails.getId())
                .orderId(orderDetails.getOrder().getId())
                .productId(orderDetails.getProduct().getId())
                .price(orderDetails.getProduct().getPrice())
                .numberOfProducts(orderDetails.getNumberOfProducts())
                .totalMoney(orderDetails.getTotalMoney())
                .color(orderDetails.getColor())
                .build();
    }
}
