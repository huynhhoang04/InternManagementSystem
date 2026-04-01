package com.example.internmanagementsystem.mapper;

import com.example.internmanagementsystem.dto.response.AssessmentResultResponse;
import com.example.internmanagementsystem.entity.AssessmentResult;
import org.springframework.stereotype.Component;

@Component
public class AssessmentResultMapper {
    public AssessmentResultResponse toResponse(AssessmentResult result) {
        if (result == null) return null;

        return AssessmentResultResponse.builder()
                .resultId(result.getResultId())
                .assignmentId(result.getAssignment() != null ? result.getAssignment().getAssignmentId() : null)
                .studentName(result.getAssignment() != null && result.getAssignment().getStudent() != null && result.getAssignment().getStudent().getUser() != null
                        ? result.getAssignment().getStudent().getUser().getFullName() : null)
                .evaluateBy(result.getEvaluatedBy() != null && result.getEvaluatedBy().getUser() != null
                        ? result.getEvaluatedBy().getUser().getFullName() : null)
                .roundId(result.getRound() != null ? result.getRound().getRoundId() : null)
                .roundName(result.getRound() != null ? result.getRound().getRoundName() : null)
                .criterionId(result.getCriterion() != null ? result.getCriterion().getCriterionId() : null)
                .criterionName(result.getCriterion() != null ? result.getCriterion().getCriterionName() : null)

                .score(result.getScore())
                .comments(result.getComments())
                .build();
    }
}
