package com.example.internmanagementsystem.controller;

import com.example.internmanagementsystem.dto.request.StudentRequest;
import com.example.internmanagementsystem.dto.request.StudentUpdateRequest;
import com.example.internmanagementsystem.dto.response.ApiResponse;
import com.example.internmanagementsystem.dto.response.StudentResponse;
import com.example.internmanagementsystem.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        return ResponseEntity.ok(ApiResponse.success(studentService.getAllStudents()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(ApiResponse.success(studentService.getStudentById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(@Valid @RequestBody StudentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(studentService.createStudent(request)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(
            @PathVariable("id") Integer id,
            @Valid @RequestBody StudentUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(studentService.updateStudent(id, request)));
    }
}
