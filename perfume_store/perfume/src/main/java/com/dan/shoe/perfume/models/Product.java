package com.dan.shoe.perfume.models;

import com.dan.shoe.perfume.models.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(columnDefinition = "nvarchar(255)")
    String name;
    @Column(columnDefinition = "TEXT")
    String description;
    int price;
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    Brand brand;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    boolean status = true;
    Gender gender;
    boolean deleted = false;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<ProductVariant> productVariants;
}
