package com.example.internmanagementsystem.service.impl;

import com.example.internmanagementsystem.dto.request.AssessmentRoundRequest;
import com.example.internmanagementsystem.dto.request.CriterionWeightRequest;
import com.example.internmanagementsystem.dto.response.AssessmentRoundResponse;
import com.example.internmanagementsystem.entity.AssessmentRound;
import com.example.internmanagementsystem.entity.EvaluationCriterion;
import com.example.internmanagementsystem.entity.InternshipPhase;
import com.example.internmanagementsystem.entity.RoundCriterion;
import com.example.internmanagementsystem.mapper.AssessmentRoundMapper;
import com.example.internmanagementsystem.repository.AssessmentRoundRepository;
import com.example.internmanagementsystem.repository.EvaluationCriterionRepository;
import com.example.internmanagementsystem.repository.InternshipPhaseRepository;
import com.example.internmanagementsystem.repository.RoundCriterionRepository;
import com.example.internmanagementsystem.service.AssessmentRoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessmentRoundServiceImpl implements AssessmentRoundService {
    @Autowired
    private AssessmentRoundRepository roundRepository;
    @Autowired
    private InternshipPhaseRepository phaseRepository;
    @Autowired
    private EvaluationCriterionRepository criterionRepository;
    @Autowired
    private RoundCriterionRepository roundCriterionRepository;
    @Autowired
    private AssessmentRoundMapper roundMapper;

    @Override
    public List<AssessmentRoundResponse> getAllRounds() {
        return roundRepository.findAll().stream()
                .map(roundMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssessmentRoundResponse> getRoundsByPhaseId(Integer phaseId) {
        return roundRepository.findByPhase_PhaseId(phaseId).stream()
                .map(roundMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AssessmentRoundResponse getRoundById(Integer id) {
        AssessmentRound round = roundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đợt đánh giá!"));
        return roundMapper.toResponse(round);
    }

    @Transactional
    @Override
    public AssessmentRoundResponse createRound(AssessmentRoundRequest request) {
        InternshipPhase phase = phaseRepository.findById(request.getPhaseId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Giai đoạn thực tập (Phase)!"));

        // 1. Lưu bảng Cha (AssessmentRound) trước
        AssessmentRound round = AssessmentRound.builder()
                .phase(phase)
                .roundName(request.getRoundName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        AssessmentRound savedRound = roundRepository.save(round);

        saveRoundCriteria(savedRound, request.getCriteria());

        return roundMapper.toResponse(savedRound);
    }

    @Transactional
    @Override
    public AssessmentRoundResponse updateRound(Integer id, AssessmentRoundRequest request) {
        AssessmentRound round = roundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đợt đánh giá!"));

        InternshipPhase phase = phaseRepository.findById(request.getPhaseId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Giai đoạn thực tập (Phase)!"));

        round.setPhase(phase);
        round.setRoundName(request.getRoundName());
        round.setStartDate(request.getStartDate());
        round.setEndDate(request.getEndDate());
        AssessmentRound savedRound = roundRepository.save(round);

        roundCriterionRepository.deleteCriteriaByRoundId(savedRound.getRoundId());
        roundCriterionRepository.flush();
        saveRoundCriteria(savedRound, request.getCriteria());

        return roundMapper.toResponse(savedRound);
    }

    @Transactional
    @Override
    public void deleteRound(Integer id) {
        if (!roundRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy đợt đánh giá!");
        }
        roundCriterionRepository.deleteCriteriaByRoundId(id);
        roundCriterionRepository.flush();
        roundRepository.deleteById(id);
    }

    private void saveRoundCriteria(AssessmentRound round, List<CriterionWeightRequest> criteriaRequests) {
        List<RoundCriterion> roundCriteria = criteriaRequests.stream().map(req -> {
            EvaluationCriterion criterion = criterionRepository.findById(req.getCriterionId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Tiêu chí có ID: " + req.getCriterionId()));

            return RoundCriterion.builder()
                    .round(round)
                    .criterion(criterion)
                    .weight(req.getWeight())
                    .build();
        }).collect(Collectors.toList());

        roundCriterionRepository.saveAll(roundCriteria);
    }
}
