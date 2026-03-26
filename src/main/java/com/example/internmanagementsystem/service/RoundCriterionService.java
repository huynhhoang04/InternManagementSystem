package com.example.internmanagementsystem.service;

import com.example.internmanagementsystem.dto.request.RoundCriterionRequest;
import com.example.internmanagementsystem.dto.request.RoundCriterionUpdateRequest;
import com.example.internmanagementsystem.dto.response.RoundCriterionResponse;

import java.util.List;

public interface RoundCriterionService {
    List<RoundCriterionResponse> getCriteriaByRoundId(Integer roundId);

    RoundCriterionResponse getRoundCriterionById(Integer id);

    RoundCriterionResponse createRoundCriterion(RoundCriterionRequest request);

    RoundCriterionResponse updateRoundCriterionWeight(Integer id, RoundCriterionUpdateRequest request);

    void deleteRoundCriterion(Integer id);
}
