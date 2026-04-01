package com.example.internmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "mentors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mentor extends BaseEntity {

    @Id
    private Integer mentorId;

    @OneToOne
    @MapsId
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mentor_id")
    private User user;

    @Column(length = 100)
    private String department;

    @Column(length = 50)
    private String academicRank;
}
