package com.example.internmanagementsystem.entity;

import com.example.internmanagementsystem.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(columnDefinition = "boolean default true")
    private Boolean isActive = true;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,  fetch = FetchType.LAZY, orphanRemoval = true)
    private Mentor mentor;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,  fetch = FetchType.LAZY, orphanRemoval = true)
    private Student student;
}