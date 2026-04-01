package com.example.internmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "assessment_rounds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentRound extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roundId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "phase_id", nullable = false)
    private InternshipPhase phase;

    @Column(nullable = false, length = 100)
    private String roundName;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "boolean default true")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "round", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoundCriterion> roundCriterions;

    @OneToMany(mappedBy = "round", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AssessmentResult> assessmentResults;
}
