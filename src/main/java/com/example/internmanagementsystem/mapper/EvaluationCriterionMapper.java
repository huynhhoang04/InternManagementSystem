package com.example.internmanagementsystem.mapper;

import com.example.internmanagementsystem.dto.response.EvaluationCriterionResponse;
import com.example.internmanagementsystem.entity.EvaluationCriterion;
import org.springframework.stereotype.Component;

@Component
public class EvaluationCriterionMapper {
    public EvaluationCriterionResponse toResponse(EvaluationCriterion criterion) {
        if (criterion == null) return null;
        return EvaluationCriterionResponse.builder()
                .criterionId(criterion.getCriterionId())
                .criterionName(criterion.getCriterionName())
                .maxScore(criterion.getMaxScore())
                .description(criterion.getDescription())
                .build();
    }
}
