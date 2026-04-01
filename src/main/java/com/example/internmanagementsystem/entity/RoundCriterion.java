package com.example.internmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "round_criteria", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"round_id", "criterion_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoundCriterion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roundCriterionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "round_id", nullable = false)
    private AssessmentRound round;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "criterion_id", nullable = false)
    private EvaluationCriterion criterion;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal weight;
}
