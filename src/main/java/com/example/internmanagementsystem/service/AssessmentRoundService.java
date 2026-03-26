package com.example.internmanagementsystem.service;

import com.example.internmanagementsystem.dto.request.AssessmentRoundRequest;
import com.example.internmanagementsystem.dto.response.AssessmentRoundResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AssessmentRoundService {
    List<AssessmentRoundResponse> getAllRounds();

    List<AssessmentRoundResponse> getRoundsByPhaseId(Integer phaseId);

    AssessmentRoundResponse getRoundById(Integer id);

    @Transactional
    AssessmentRoundResponse createRound(AssessmentRoundRequest request);

    @Transactional
    AssessmentRoundResponse updateRound(Integer id, AssessmentRoundRequest request);

    @Transactional
    void deleteRound(Integer id);
}
