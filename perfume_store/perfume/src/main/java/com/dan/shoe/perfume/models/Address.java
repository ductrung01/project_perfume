package com.dan.shoe.perfume.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(columnDefinition = "nvarchar(255)")
    String province; // Tỉnh/Thành phố

    @Column(columnDefinition = "nvarchar(255)")
    String district; // Quận/Huyện

    @Column(columnDefinition = "nvarchar(255)")
    String ward; // Phường/Xã

    @Column(columnDefinition = "nvarchar(255)")
    String street; // Đường

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user; // Tham chiếu đến người dùng

    boolean primaryAddress = false; // Địa chỉ chính
}