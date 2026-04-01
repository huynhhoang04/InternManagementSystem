package com.example.internmanagementsystem.repository;

import com.example.internmanagementsystem.entity.AssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Integer> {
    List<AssessmentResult> findByAssignment_AssignmentId(Integer assignmentId);

    List<AssessmentResult> findByAssignment_AssignmentIdAndAssignment_Student_StudentId(Integer assignmentId, Integer userId);

    List<AssessmentResult> findByAssignment_AssignmentIdAndEvaluatedBy_MentorId(Integer assignmentId, Integer userId);

    List<AssessmentResult> findByEvaluatedBy_MentorId(Integer mentorId);

    List<AssessmentResult> findByEvaluatedBy_MentorIdAndAssignment_Student_StudentId(Integer mentorId, Integer userId);

    List<AssessmentResult> findByEvaluatedBy_MentorIdAndAssignment_AssignmentId(Integer mentorId, Integer assignmentId);

    List<AssessmentResult> findByEvaluatedBy_MentorIdAndAssignment_Student_StudentIdAndAssignment_AssignmentId(Integer mentorId, Integer userId, Integer assignmentId);

    List<AssessmentResult> findByAssignment_Student_StudentId(Integer studentId);

    List<AssessmentResult> findByAssignment_Student_StudentIdAndAssignment_AssignmentIdAndEvaluatedBy_MentorId(Integer studentId, Integer assignmentId, Integer userId);

    List<AssessmentResult> findByAssignment_Student_StudentIdAndAssignment_AssignmentId(Integer studentId, Integer assignmentId);

    List<AssessmentResult> findByAssignment_Student_StudentIdAndEvaluatedBy_MentorId(Integer studentId, Integer userId);

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
