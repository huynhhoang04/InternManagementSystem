package com.example.internmanagementsystem.mapper;

import com.example.internmanagementsystem.dto.response.AssessmentRoundResponse;
import com.example.internmanagementsystem.dto.response.CriterionWeightResponse;
import com.example.internmanagementsystem.entity.AssessmentRound;
import com.example.internmanagementsystem.repository.RoundCriterionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssessmentRoundMapper {

    private final RoundCriterionRepository roundCriterionRepository;

    public AssessmentRoundResponse toResponse(AssessmentRound round) {
        if (round == null) return null;

        List<CriterionWeightResponse> criteriaList = roundCriterionRepository.getCriteriaByRoundId(round.getRoundId())
                .stream()
                .map(rc -> CriterionWeightResponse.builder()
                        .criterionId(rc.getCriterion().getCriterionId())
                        .criterionName(rc.getCriterion().getCriterionName())
                        .weight(rc.getWeight())
                        .build())
                .collect(Collectors.toList());

        return AssessmentRoundResponse.builder()
                .roundId(round.getRoundId())
                .phaseId(round.getPhase() != null ? round.getPhase().getPhaseId() : null)
                .phaseName(round.getPhase() != null ? round.getPhase().getPhaseName() : null)
                .roundName(round.getRoundName())
                .startDate(round.getStartDate())
                .endDate(round.getEndDate())
                .criteria(criteriaList)
                .build();
    }
}
