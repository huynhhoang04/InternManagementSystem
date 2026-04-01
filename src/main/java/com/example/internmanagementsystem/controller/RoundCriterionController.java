package com.example.internmanagementsystem.controller;

import com.example.internmanagementsystem.dto.request.RoundCriterionRequest;
import com.example.internmanagementsystem.dto.request.RoundCriterionUpdateRequest;
import com.example.internmanagementsystem.dto.response.ApiResponse;
import com.example.internmanagementsystem.dto.response.RoundCriterionResponse;
import com.example.internmanagementsystem.service.RoundCriterionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/round_criteria")
@RequiredArgsConstructor
public class RoundCriterionController {
    @Autowired
    private RoundCriterionService rcService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoundCriterionResponse>>> getAll(
            @RequestParam(name = "round_id", required = false) Integer roundId) {
        return ResponseEntity.ok(ApiResponse.success(rcService.getCriteriaByRoundId(roundId)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoundCriterionResponse>> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(ApiResponse.success(rcService.getRoundCriterionById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<RoundCriterionResponse>> create(@Valid @RequestBody RoundCriterionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(rcService.createRoundCriterion(request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoundCriterionResponse>> updateWeight(
            @PathVariable("id") Integer id,
            @Valid @RequestBody RoundCriterionUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(rcService.updateRoundCriterionWeight(id,request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Integer id) {
        rcService.deleteRoundCriterion(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
