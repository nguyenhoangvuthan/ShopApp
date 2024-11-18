package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "fullname", length = 100)
    String fullName;

    @Column(length = 100)
    String email;

    @Column(name = "phone_number", nullable = false, length = 100)
    String phoneNumber;

    @Column(name = "address", length = 100)
    String address;

    @Column(length = 100)
    String note;

    @Column(name = "order_date")
    LocalDateTime orderDate;

    @Column(nullable = false)
    String status;

    @Column(name = "total_money", nullable = false)
    Float totalMoney;

    @Column(name = "shipping_method")
    String shippingMethod;

    @Column(name = "shipping_address")
    String shippingAddress;

    @Column(name = "shipping_date")
    Date shippingDate;

    @Column(name = "tracking_number")
    String trackingNumber;

    @Column(name = "payment_method")
    String paymentMethod;

    Boolean active;
}
