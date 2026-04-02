package com.example.internmanagementsystem.repository;

import com.example.internmanagementsystem.entity.AssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Integer> {
    @Query("SELECT r FROM AssessmentResult r WHERE " +
            "(:assignmentId IS NULL OR r.assignment.assignmentId = :assignmentId) AND " +
            "(:studentId IS NULL OR r.assignment.student.studentId = :studentId) AND " +
            "(:mentorId IS NULL OR r.evaluatedBy.mentorId = :mentorId)")
    List<AssessmentResult> findFilteredResults(
            @Param("assignmentId") Integer assignmentId,
            @Param("studentId") Integer studentId,
            @Param("mentorId") Integer mentorId
    );

    boolean existsByAssignment_AssignmentIdAndRound_RoundIdAndCriterion_CriterionId(Integer assignmentId, Integer roundId, Integer criterionId);
}
