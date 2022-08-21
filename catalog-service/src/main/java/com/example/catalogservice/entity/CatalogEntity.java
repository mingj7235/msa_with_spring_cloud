package com.example.catalogservice.entity;

import com.example.catalogservice.dto.CatalogDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@Table (name = "catalog")
public class CatalogEntity implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 120, unique = true)
    private String productId;

    @Column (nullable = false)
    private String productName;

    @Column (nullable = false)
    private Integer stock;

    @Column (nullable = false)
    private Integer unitPrice;

    @Column (nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    public static CatalogEntity toEntity (final @NotNull CatalogDto request) {
        return CatalogEntity.builder()
                .productId(request.getProductId())
                .unitPrice(request.getUnitPrice())
                .build();
    }
}
