package com.example.internmanagementsystem.mapper;

import com.example.internmanagementsystem.dto.response.AssignmentResponse;
import com.example.internmanagementsystem.entity.InternshipAssignment;
import org.springframework.stereotype.Component;

@Component
public class AssignmentMapper {
    public AssignmentResponse toResponse(InternshipAssignment assignment) {
        if (assignment == null) return null;
        return AssignmentResponse.builder()
                .assignmentId(assignment.getAssignmentId()) // Thay bằng tên ID thực tế trong Entity
                .studentId(assignment.getStudent() != null ? assignment.getStudent().getStudentId() : null)
                .studentName(assignment.getStudent() != null && assignment.getStudent().getUser() != null ? assignment.getStudent().getUser().getFullName() : null)
                .studentCode(assignment.getStudent() != null ? assignment.getStudent().getStudentCode() : null)
                .mentorId(assignment.getMentor() != null ? assignment.getMentor().getMentorId() : null)
                .mentorName(assignment.getMentor() != null && assignment.getMentor().getUser() != null ? assignment.getMentor().getUser().getFullName() : null)
                .phaseId(assignment.getPhase() != null ? assignment.getPhase().getPhaseId() : null)
                .phaseName(assignment.getPhase() != null ? assignment.getPhase().getPhaseName() : null)
                .status(assignment.getStatus() != null ? assignment.getStatus().toString() : null)
                .build();
    }
}
