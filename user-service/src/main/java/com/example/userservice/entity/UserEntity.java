package com.example.userservice.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 50, unique = true)
    private String email;

    @Column (nullable = false, length = 50)
    private String name;

    @Column (nullable = false, unique = true)
    private String userId;

    @Column (nullable = false, unique = true)
    private String encryptedPwd;
}
