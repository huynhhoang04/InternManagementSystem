package com.example.internmanagementsystem.repository;

import com.example.internmanagementsystem.entity.AssessmentRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentRoundRepository extends JpaRepository<AssessmentRound, Integer> {
    List<AssessmentRound> findByPhase_PhaseId(Integer phaseId);
}