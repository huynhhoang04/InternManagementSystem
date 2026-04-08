package com.example.internmanagementsystem.repository;

import com.example.internmanagementsystem.entity.InternshipAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternshipAssignmentRepository extends JpaRepository<InternshipAssignment, Integer> {
    List<InternshipAssignment> findByMentor_MentorId(Integer mentorId);

    List<InternshipAssignment> findByStudent_StudentId(Integer studentId);

    boolean existsByStudent_StudentIdAndPhase_PhaseId(Integer studentId, Integer phaseId);

    List<InternshipAssignment> findByStudent_StudentIdOrMentor_MentorId(Integer studentId, Integer mentorId);
}
