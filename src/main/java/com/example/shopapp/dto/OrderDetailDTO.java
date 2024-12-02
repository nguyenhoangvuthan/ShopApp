package com.example.shopapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1, message = "Order's ID must be > 0")
    Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "product's ID must be > 0")
    Long productId;

    @Min(value = 0, message = "Price must be >= 0")
    Float price;

    @JsonProperty("number_of_products")
    @Min(value = 1, message = "number of product must be > 1")
    int numberOfProduct;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Price must be >= 0")
    Float totalMoney;

    String color;
}
