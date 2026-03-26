package com.example.internmanagementsystem.mapper;

import com.example.internmanagementsystem.dto.response.RoundCriterionResponse;
import com.example.internmanagementsystem.entity.RoundCriterion;
import org.springframework.stereotype.Component;

@Component
public class RoundCriterionMapper {
    public RoundCriterionResponse toResponse(RoundCriterion rc) {
        if (rc == null) return null;
        return RoundCriterionResponse.builder()
                .roundCriteriaId(rc.getRoundCriterionId())
                .roundId(rc.getRound() != null ? rc.getRound().getRoundId() : null)
                .criterionId(rc.getCriterion() != null ? rc.getCriterion().getCriterionId() : null)
                .criterionName(rc.getCriterion() != null ? rc.getCriterion().getCriterionName() : null)
                .weight(rc.getWeight())
                .build();
    }
}
