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
@Table(name = "social_accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 20)
    String provider;

    @Column(nullable = false, length = 50)
    String providerId;

    @Column(nullable = false, length = 150)
    String name;

    @Column(nullable = false, length = 150)
    String email;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
