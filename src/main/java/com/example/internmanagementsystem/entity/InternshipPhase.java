package com.example.internmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "internship_phases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipPhase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer phaseId;

    @Column(unique = true, nullable = false, length = 100)
    private String phaseName;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "phase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AssessmentRound> assessmentRounds;

    @OneToMany(mappedBy = "phase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InternshipAssignment> internshipAssignments;
}
