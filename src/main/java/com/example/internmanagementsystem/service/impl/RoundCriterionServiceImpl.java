package com.example.internmanagementsystem.service.impl;

import com.example.internmanagementsystem.dto.request.RoundCriterionRequest;
import com.example.internmanagementsystem.dto.request.RoundCriterionUpdateRequest;
import com.example.internmanagementsystem.dto.response.RoundCriterionResponse;
import com.example.internmanagementsystem.entity.AssessmentRound;
import com.example.internmanagementsystem.entity.EvaluationCriterion;
import com.example.internmanagementsystem.entity.RoundCriterion;
import com.example.internmanagementsystem.mapper.RoundCriterionMapper;
import com.example.internmanagementsystem.repository.AssessmentRoundRepository;
import com.example.internmanagementsystem.repository.EvaluationCriterionRepository;
import com.example.internmanagementsystem.repository.RoundCriterionRepository;
import com.example.internmanagementsystem.service.RoundCriterionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoundCriterionServiceImpl implements RoundCriterionService {
    @Autowired
    private RoundCriterionRepository roundCriterionRepository;
    @Autowired
    private AssessmentRoundRepository roundRepository;
    @Autowired
    private EvaluationCriterionRepository criterionRepository;
    @Autowired
    private RoundCriterionMapper mapper;

    @Override
    public List<RoundCriterionResponse> getCriteriaByRoundId(Integer roundId) {
        if (roundId == null) {
            return roundCriterionRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
        }
        return roundCriterionRepository.getCriteriaByRoundId(roundId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoundCriterionResponse getRoundCriterionById(Integer id) {
        RoundCriterion rc = roundCriterionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi này!"));
        return mapper.toResponse(rc);
    }

    @Override
    public RoundCriterionResponse createRoundCriterion(RoundCriterionRequest request) {
        AssessmentRound round = roundRepository.findById(request.getRoundId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Đợt đánh giá!"));
        EvaluationCriterion criterion = criterionRepository.findById(request.getCriterionId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Tiêu chí này!"));

        boolean exists = roundCriterionRepository.checkIfExists(
                request.getRoundId(), request.getCriterionId());
        if (exists) {
            throw new RuntimeException("Tiêu chí này đã tồn tại trong đợt đánh giá!");
        }

        RoundCriterion rc = RoundCriterion.builder()
                .round(round)
                .criterion(criterion)
                .weight(request.getWeight())
                .build();

        return mapper.toResponse(roundCriterionRepository.save(rc));
    }

    @Override
    public RoundCriterionResponse updateRoundCriterionWeight(Integer id, RoundCriterionUpdateRequest request) {
        RoundCriterion rc = roundCriterionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi này!"));

        rc.setWeight(request.getWeight());
        return mapper.toResponse(roundCriterionRepository.save(rc));
    }

    @Override
    public void deleteRoundCriterion(Integer id) {
        if (!roundCriterionRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy bản ghi này!");
        }
        roundCriterionRepository.deleteById(id);
    }
}
