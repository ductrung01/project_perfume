package com.dan.shoe.perfume.models;

import com.dan.shoe.perfume.models.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "staffs")
public class Staff {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    String name;
    String phoneNumber;
    LocalDate dob;
    @Column(columnDefinition = "nvarchar(255)")
    String address;
    Gender gender;
    String cccd;
    String imageCode;
    boolean status = true;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    User user;
}
