package com.example.userservice.entity;

import com.example.userservice.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    public static UserEntity register (final @NotNull UserDto request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .name(request.getName())
                .userId(request.getUserId())
                .encryptedPwd(request.getEncryptedPwd())
                .build();
    }
}
