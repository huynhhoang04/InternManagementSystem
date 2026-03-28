package com.example.internmanagementsystem.repository;

import com.example.internmanagementsystem.entity.AssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Integer> {
    List<AssessmentResult> findByAssignment_AssignmentId(Integer assignmentId);

    List<AssessmentResult> findByAssignment_Mentor_MentorId(Integer mentorId);

    List<AssessmentResult> findByAssignment_Student_StudentId(Integer studentId);

    boolean existsByAssignment_AssignmentIdAndRound_RoundIdAndCriterion_CriterionId(Integer assignmentId, Integer roundId, Integer criterionId);
}
