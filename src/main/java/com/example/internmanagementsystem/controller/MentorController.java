package com.example.internmanagementsystem.controller;

import com.example.internmanagementsystem.dto.request.MentorRequest;
import com.example.internmanagementsystem.dto.request.MentorUpdateRequest;
import com.example.internmanagementsystem.dto.response.ApiResponse;
import com.example.internmanagementsystem.dto.response.MentorResponse;
import com.example.internmanagementsystem.service.MentorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentors")
@RequiredArgsConstructor
public class MentorController {
    @Autowired
    private MentorService mentorService;

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MentorResponse>>> getAllMentors() {
        return ResponseEntity.ok(ApiResponse.success(mentorService.getAllMentors()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MentorResponse>> getMentorById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(ApiResponse.success(mentorService.getMentorById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<MentorResponse>> createMentor(@Valid @RequestBody MentorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(mentorService.createMentor(request)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MentorResponse>> updateMentor(
            @PathVariable("id") Integer id,
            @Valid @RequestBody MentorUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(mentorService.updateMentor(id,request)));
    }
}
