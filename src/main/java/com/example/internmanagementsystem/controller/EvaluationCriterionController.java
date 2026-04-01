package com.example.internmanagementsystem.controller;

import com.example.internmanagementsystem.dto.request.EvaluationCriterionRequest;
import com.example.internmanagementsystem.dto.response.ApiResponse;
import com.example.internmanagementsystem.dto.response.EvaluationCriterionResponse;
import com.example.internmanagementsystem.service.EvaluationCriterionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation_criteria")
@RequiredArgsConstructor
public class EvaluationCriterionController {
    @Autowired
    private EvaluationCriterionService criterionService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EvaluationCriterionResponse>>> getAllCriteria() {
        return ResponseEntity.ok(ApiResponse.success(criterionService.getAllCriteria()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping("/{criterion_id}")
    public ResponseEntity<ApiResponse<EvaluationCriterionResponse>> getCriterionById(@PathVariable("criterion_id") Integer criterionId) {
        return ResponseEntity.ok(ApiResponse.success(criterionService.getCriterionById(criterionId)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<EvaluationCriterionResponse>> createCriterion(@Valid @RequestBody EvaluationCriterionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(criterionService.createCriterion(request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{criterion_id}")
    public ResponseEntity<ApiResponse<EvaluationCriterionResponse>> updateCriterion(
            @PathVariable("criterion_id") Integer criterionId,
            @Valid @RequestBody EvaluationCriterionRequest request) {
        return ResponseEntity.ok(ApiResponse.success(criterionService.updateCriterion(criterionId, request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{criterion_id}")
    public ResponseEntity<ApiResponse<Void>> deleteCriterion(@PathVariable("criterion_id") Integer criterionId) {
        criterionService.deleteCriterion(criterionId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
