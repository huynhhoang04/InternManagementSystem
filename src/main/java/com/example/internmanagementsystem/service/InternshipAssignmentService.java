package com.example.internmanagementsystem.service;

import com.example.internmanagementsystem.dto.request.AssignmentRequest;
import com.example.internmanagementsystem.dto.request.AssignmentStatusUpdateRequest;
import com.example.internmanagementsystem.dto.response.AssignmentResponse;

import java.util.List;

public interface InternshipAssignmentService {
    List<AssignmentResponse> getAllAssignments(Integer userId);

    AssignmentResponse getAssignmentById(Integer id);

    AssignmentResponse createAssignment(AssignmentRequest request);

    AssignmentResponse updateStatus(Integer id, AssignmentStatusUpdateRequest request);
}
