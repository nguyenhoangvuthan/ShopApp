package com.example.shopapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse extends BaseResponse {
    Long id;

    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("fullname")
    String fullName;

    @JsonProperty("phone")
    String phoneNumber;

    String address;

    String note;

    @JsonProperty("order_date")
    LocalDateTime orderDate;

    String status;

    @JsonProperty("total_money")
    Float totalMoney;

    @JsonProperty("shipping_address")
    String shippingAddress;

    @JsonProperty("shipping_date")
    Date shippingDate;

    @JsonProperty("tracking_number")
    String trackingNumber;

    @JsonProperty("payment_method")
    String paymentMethod;

    Boolean active;

}