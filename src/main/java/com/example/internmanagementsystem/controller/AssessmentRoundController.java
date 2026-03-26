package com.example.internmanagementsystem.controller;

import com.example.internmanagementsystem.dto.request.AssessmentRoundRequest;
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
    public ResponseEntity<List<AssessmentRoundResponse>> getAllAssessmentRounds(
            @RequestParam(name = "phase_id", required = false) Integer phaseId) {

        if (phaseId != null) {
            return ResponseEntity.ok(assessmentRoundService.getRoundsByPhaseId(phaseId));
        }
        return ResponseEntity.ok(assessmentRoundService.getAllRounds());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping("/{round_id}")
    public ResponseEntity<AssessmentRoundResponse> getRoundById(@PathVariable("round_id") Integer roundId) {
        return ResponseEntity.ok(assessmentRoundService.getRoundById(roundId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AssessmentRoundResponse> createRound(@Valid @RequestBody AssessmentRoundRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assessmentRoundService.createRound(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{round_id}")
    public ResponseEntity<AssessmentRoundResponse> updateRound(
            @PathVariable("round_id") Integer roundId,
            @Valid @RequestBody AssessmentRoundRequest request) {
        return ResponseEntity.ok(assessmentRoundService.updateRound(roundId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{round_id}")
    public ResponseEntity<Void> deleteRound(@PathVariable("round_id") Integer roundId) {
        assessmentRoundService.deleteRound(roundId);
        return ResponseEntity.noContent().build();
    }
}
