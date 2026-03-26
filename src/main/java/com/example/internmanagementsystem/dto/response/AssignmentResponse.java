package com.example.internmanagementsystem.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssignmentResponse {
    private Integer assignmentId;
    private Integer studentId;
    private String studentName;
    private String studentCode;
    private Integer mentorId;
    private String mentorName;
    private Integer phaseId;
    private String phaseName;
    private String status;
}
