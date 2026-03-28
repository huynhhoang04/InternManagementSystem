package com.example.internmanagementsystem.service;

import com.example.internmanagementsystem.dto.request.AssessmentResultRequest;
import com.example.internmanagementsystem.dto.request.AssessmentResultUpdateRequest;
import com.example.internmanagementsystem.dto.response.AssessmentResultResponse;

import java.util.List;

public interface AssessmentResultService {
    List<AssessmentResultResponse> getResults(Integer assignmentId);

    AssessmentResultResponse createResult(AssessmentResultRequest request);

    AssessmentResultResponse updateResult(Integer id, AssessmentResultUpdateRequest request);
}
