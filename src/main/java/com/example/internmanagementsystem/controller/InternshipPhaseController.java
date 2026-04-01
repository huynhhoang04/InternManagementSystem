package com.example.internmanagementsystem.controller;

import com.example.internmanagementsystem.dto.request.InternshipPhaseRequest;
import com.example.internmanagementsystem.dto.response.ApiResponse;
import com.example.internmanagementsystem.dto.response.InternshipPhaseResponse;
import com.example.internmanagementsystem.service.InternshipPhaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internship_phases")
@RequiredArgsConstructor
public class InternshipPhaseController {
    @Autowired
    private InternshipPhaseService phaseService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<InternshipPhaseResponse>>> getAllPhases() {
        return ResponseEntity.ok(ApiResponse.success(phaseService.getAllPhases()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping("/{phase_id}")
    public ResponseEntity<ApiResponse<InternshipPhaseResponse>> getPhaseById(@PathVariable("phase_id") Integer phaseId) {
        return ResponseEntity.ok(ApiResponse.success(phaseService.getPhaseById(phaseId)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<InternshipPhaseResponse>> createPhase(@Valid @RequestBody InternshipPhaseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(phaseService.createPhase(request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{phase_id}")
    public ResponseEntity<ApiResponse<InternshipPhaseResponse>> updatePhase(
            @PathVariable("phase_id") Integer phaseId,
            @Valid @RequestBody InternshipPhaseRequest request) {
        return ResponseEntity.ok(ApiResponse.success(phaseService.updatePhase(phaseId, request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{phase_id}")
    public ResponseEntity<ApiResponse<Void>> deletePhase(@PathVariable("phase_id") Integer phaseId) {
        phaseService.deletePhase(phaseId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
