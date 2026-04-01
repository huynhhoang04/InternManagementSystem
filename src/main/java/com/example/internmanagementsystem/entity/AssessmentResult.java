package com.example.internmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "assessment_results", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"assignment_id", "round_id", "criterion_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "assignment_id", nullable = false)
    private InternshipAssignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "round_id", nullable = false)
    private AssessmentRound round;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criterion_id", nullable = false)
    private EvaluationCriterion criterion;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal score;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluated_by", nullable = false)
    private Mentor evaluatedBy;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime evaluationDate;
}
