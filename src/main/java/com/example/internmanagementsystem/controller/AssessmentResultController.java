package com.example.internmanagementsystem.controller;

import com.example.internmanagementsystem.dto.request.AssessmentResultRequest;
import com.example.internmanagementsystem.dto.request.AssessmentResultUpdateRequest;
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
    public ResponseEntity<List<AssessmentResultResponse>> getResults(
            @RequestParam(name = "assignment_id", required = false) Integer assignmentId) {
        return ResponseEntity.ok(resultService.getResults(assignmentId));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping
    public ResponseEntity<AssessmentResultResponse> createResult(@Valid @RequestBody AssessmentResultRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resultService.createResult(request));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PutMapping("/{result_id}")
    public ResponseEntity<AssessmentResultResponse> updateResult(
            @PathVariable("result_id") Integer resultId,
            @Valid @RequestBody AssessmentResultUpdateRequest request) {
        return ResponseEntity.ok(resultService.updateResult(resultId, request));
    }
}
