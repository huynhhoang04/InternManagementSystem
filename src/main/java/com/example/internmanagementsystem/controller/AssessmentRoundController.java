package com.example.internmanagementsystem.controller;

import com.example.internmanagementsystem.dto.request.AssessmentRoundRequest;
import com.example.internmanagementsystem.dto.response.ApiResponse;
import com.example.internmanagementsystem.dto.response.AssessmentRoundResponse;
import com.example.internmanagementsystem.service.AssessmentRoundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessment_rounds")
@RequiredArgsConstructor
public class AssessmentRoundController {
    @Autowired
    private AssessmentRoundService assessmentRoundService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AssessmentRoundResponse>>> getAllAssessmentRounds(
            @RequestParam(name = "phase_id", required = false) Integer phaseId) {

        if (phaseId != null) {
            return ResponseEntity.ok(ApiResponse.success(assessmentRoundService.getRoundsByPhaseId(phaseId)));
        }
        return ResponseEntity.ok(ApiResponse.success(assessmentRoundService.getAllRounds()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping("/{round_id}")
    public ResponseEntity<ApiResponse<AssessmentRoundResponse>> getRoundById(@PathVariable("round_id") Integer roundId) {
        return ResponseEntity.ok(ApiResponse.success(assessmentRoundService.getRoundById(roundId)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<AssessmentRoundResponse>> createRound(@Valid @RequestBody AssessmentRoundRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(assessmentRoundService.createRound(request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{round_id}")
    public ResponseEntity<ApiResponse<AssessmentRoundResponse>> updateRound(
            @PathVariable("round_id") Integer roundId,
            @Valid @RequestBody AssessmentRoundRequest request) {
        return ResponseEntity.ok(ApiResponse.success(assessmentRoundService.updateRound(roundId, request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{round_id}")
    public ResponseEntity<ApiResponse<Void>> deleteRound(@PathVariable("round_id") Integer roundId) {
        assessmentRoundService.deleteRound(roundId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
