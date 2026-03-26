package com.example.internmanagementsystem.repository;

import com.example.internmanagementsystem.entity.RoundCriterion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoundCriterionRepository extends JpaRepository<RoundCriterion, Integer> {
    @Query("SELECT r FROM RoundCriterion r WHERE r.round.roundId = :roundId")
    List<RoundCriterion> getCriteriaByRoundId(@Param("roundId") Integer roundId);

    @Modifying
    @Query("DELETE FROM RoundCriterion r WHERE r.round.roundId = :roundId")
    void deleteCriteriaByRoundId(@Param("roundId") Integer roundId);
}
