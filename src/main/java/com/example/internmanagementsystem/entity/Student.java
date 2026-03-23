package com.example.internmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student extends BaseEntity {

    @Id
    private Integer studentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private User user;

    @Column(unique = true, nullable = false, length = 20)
    private String studentCode;

    @Column(length = 100)
    private String major;

    @Column(name = "class_name", length = 50)
    private String className;

    private LocalDate dateOfBirth;

    @Column(length = 255)
    private String address;
}
