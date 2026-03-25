package com.example.internmanagementsystem.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class EvaluationCriterionResponse {
    private Integer criterionId;
    private String criterionName;
    private BigDecimal maxScore;
    private String description;
}
