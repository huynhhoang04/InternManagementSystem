package com.example.internmanagementsystem.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class AssessmentResultResponse {
    private Integer resultId;
    private Integer assignmentId;
    private String studentName;
    private String evaluateBy;
    private Integer roundId;
    private String roundName;
    private Integer criterionId;
    private String criterionName;
    private BigDecimal score;
    private String comments;
}
