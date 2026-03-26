package com.example.internmanagementsystem.controller;

import com.example.internmanagementsystem.dto.request.AssignmentRequest;
import com.example.internmanagementsystem.dto.request.AssignmentStatusUpdateRequest;
import com.example.internmanagementsystem.dto.response.AssignmentResponse;
import com.example.internmanagementsystem.service.InternshipAssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internship_assignments")
@RequiredArgsConstructor
public class InternshipAssignmentController {
    @Autowired
    private InternshipAssignmentService assignmentService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> getAll() {
        return ResponseEntity.ok(assignmentService.getAllAssignments());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(assignmentService.getAssignmentById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AssignmentResponse> create(@Valid @RequestBody AssignmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assignmentService.createAssignment(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<AssignmentResponse> updateStatus(
            @PathVariable("id") Integer id,
            @Valid @RequestBody AssignmentStatusUpdateRequest request) {
        return ResponseEntity.ok(assignmentService.updateStatus(id, request));
    }
}
