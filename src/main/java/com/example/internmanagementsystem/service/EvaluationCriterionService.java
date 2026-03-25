package com.example.internmanagementsystem.service;

import com.example.internmanagementsystem.dto.request.EvaluationCriterionRequest;
import com.example.internmanagementsystem.dto.response.EvaluationCriterionResponse;

import java.util.List;

public interface EvaluationCriterionService {
    List<EvaluationCriterionResponse> getAllCriteria();

    EvaluationCriterionResponse getCriterionById(Integer id);

    EvaluationCriterionResponse createCriterion(EvaluationCriterionRequest request);

    EvaluationCriterionResponse updateCriterion(Integer id, EvaluationCriterionRequest request);

    void deleteCriterion(Integer id);
}
