package com.example.internmanagementsystem.service.impl;

import com.example.internmanagementsystem.dto.request.EvaluationCriterionRequest;
import com.example.internmanagementsystem.dto.response.EvaluationCriterionResponse;
import com.example.internmanagementsystem.entity.EvaluationCriterion;
import com.example.internmanagementsystem.mapper.EvaluationCriterionMapper;
import com.example.internmanagementsystem.repository.EvaluationCriterionRepository;
import com.example.internmanagementsystem.service.EvaluationCriterionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationCriterionServiceImpl implements EvaluationCriterionService {
    @Autowired
    private EvaluationCriterionRepository criterionRepository;
    @Autowired
    private EvaluationCriterionMapper criterionMapper;

    @Override
    public List<EvaluationCriterionResponse> getAllCriteria() {
        return criterionRepository.findAll().stream()
                .map(criterionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EvaluationCriterionResponse getCriterionById(Integer id) {
        EvaluationCriterion criterion = criterionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tiêu chí đánh giá này!"));
        return criterionMapper.toResponse(criterion);
    }

    @Override
    public EvaluationCriterionResponse createCriterion(EvaluationCriterionRequest request) {
        EvaluationCriterion criterion = EvaluationCriterion.builder()
                .criterionName(request.getCriterionName())
                .maxScore(request.getMaxScore())
                .description(request.getDescription())
                .build();

        return criterionMapper.toResponse(criterionRepository.save(criterion));
    }

    @Override
    public EvaluationCriterionResponse updateCriterion(Integer id, EvaluationCriterionRequest request) {
        EvaluationCriterion criterion = criterionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tiêu chí đánh giá này!"));

        criterion.setCriterionName(request.getCriterionName());
        criterion.setMaxScore(request.getMaxScore());
        criterion.setDescription(request.getDescription());

        return criterionMapper.toResponse(criterionRepository.save(criterion));
    }

    @Override
    public void deleteCriterion(Integer id) {
        if (!criterionRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy tiêu chí đánh giá này!");
        }
        criterionRepository.deleteById(id);
    }
}
