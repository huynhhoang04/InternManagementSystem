package com.example.internmanagementsystem.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class AssessmentRoundResponse {
    private Integer roundId;
    private Integer phaseId;
    private String phaseName;
    private String roundName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<CriterionWeightResponse> criteria;
}
