package com.example.internmanagementsystem.mapper;

import com.example.internmanagementsystem.dto.response.InternshipPhaseResponse;
import com.example.internmanagementsystem.entity.InternshipPhase;
import org.springframework.stereotype.Component;

@Component
public class InternshipPhaseMapper {
    public InternshipPhaseResponse toResponse(InternshipPhase phase) {
        if (phase == null) return null;
        return InternshipPhaseResponse.builder()
                .phaseId(phase.getPhaseId())
                .phaseName(phase.getPhaseName())
                .startDate(phase.getStartDate())
                .endDate(phase.getEndDate())
                .description(phase.getDescription())
                .build();
    }
}
