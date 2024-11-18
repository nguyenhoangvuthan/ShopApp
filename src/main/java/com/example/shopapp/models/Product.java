package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Float price;

    @Column(nullable = false, length = 350)
    String name;

    @Column(nullable = false, length = 300)
    String thumbnail;

    String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
}
