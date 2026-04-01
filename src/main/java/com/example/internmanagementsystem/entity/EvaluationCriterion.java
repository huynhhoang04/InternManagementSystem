package com.example.internmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "evaluation_criteria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationCriterion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer criterionId;

    @Column(unique = true, nullable = false, length = 200)
    private String criterionName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal maxScore;

    @OneToMany(mappedBy = "criterion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoundCriterion> roundCriterions;

    @OneToMany(mappedBy = "criterion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AssessmentResult> assessmentResults;
}
