package com.example.internmanagementsystem.controller;

import com.example.internmanagementsystem.dto.request.AssessmentResultRequest;
import com.example.internmanagementsystem.dto.request.AssessmentResultUpdateRequest;
import com.example.internmanagementsystem.dto.response.ApiResponse;
import com.example.internmanagementsystem.dto.response.AssessmentResultResponse;
import com.example.internmanagementsystem.service.AssessmentResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessment_results")
@RequiredArgsConstructor
public class AssessmentResultController {
    @Autowired
    private AssessmentResultService resultService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AssessmentResultResponse>>> getResults(
            @RequestParam(name = "assignment_id", required = false) Integer assignmentId,
            @RequestParam(name = "user_id", required = false) Integer userId)  {
        return ResponseEntity.ok(ApiResponse.success(resultService.getResults(assignmentId, userId)));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<AssessmentResultResponse>> createResult(@Valid @RequestBody AssessmentResultRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(resultService.createResult(request)));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PutMapping("/{result_id}")
    public ResponseEntity<ApiResponse<AssessmentResultResponse>> updateResult(
            @PathVariable("result_id") Integer resultId,
            @Valid @RequestBody AssessmentResultUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(resultService.updateResult(resultId, request)));
    }
}
