package com.Ecommerce_BE.Model;


import com.Ecommerce_BE.Enum.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false, length = 100)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "user_email", unique = true, nullable = false, length = 255)
    @NotBlank(message = "Email is required")
    private String email;

    @Column(name = "user_password", nullable = false)
    @NotBlank(message = "Password_number is required")
    private String password;

    @Column(name = "phone_number", length = 15)
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItemList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;

    @Column(name = "created_at", updatable = false)
    private final LocalDate createdAt = LocalDate.now();

}
