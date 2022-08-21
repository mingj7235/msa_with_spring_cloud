package com.example.orderservice.entity;

import com.example.orderservice.dto.OrderDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@Table (name = "orders")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 120, unique = true)
    private String productId;

    @Column (nullable = false)
    private Integer qty;

    @Column (nullable = false)
    private Integer unitPrice;

    @Column (nullable = false)
    private Integer totalPrice;

    @Column (nullable = false)
    private String userId;

    @Column (nullable = false, unique = true)
    private String orderId;

    @Column (nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    public static OrderEntity toEntity (final @NotNull OrderDto request) {
        return OrderEntity.builder()
                .productId(request.getProductId())
                .qty(request.getQty())
                .unitPrice(request.getUnitPrice())
                .totalPrice(request.getTotalPrice())
                .userId(request.getUserId())
                .orderId(request.getOrderId())
                .build();
    }
}
