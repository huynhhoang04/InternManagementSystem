package com.example.internmanagementsystem.service.impl;

import com.example.internmanagementsystem.dto.request.InternshipPhaseRequest;
import com.example.internmanagementsystem.dto.response.InternshipPhaseResponse;
import com.example.internmanagementsystem.entity.InternshipPhase;
import com.example.internmanagementsystem.mapper.InternshipPhaseMapper;
import com.example.internmanagementsystem.repository.InternshipPhaseRepository;
import com.example.internmanagementsystem.service.InternshipPhaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InternshipPhaseServiceImpl implements InternshipPhaseService {
    @Autowired
    private InternshipPhaseRepository phaseRepository;
    @Autowired
    private InternshipPhaseMapper phaseMapper;

    private void validateDates(InternshipPhaseRequest request) {
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new RuntimeException("Lỗi: Ngày kết thúc không thể trước ngày bắt đầu!");
        }
    }

    @Override
    public List<InternshipPhaseResponse> getAllPhases() {
        return phaseRepository.findAll().stream()
                .map(phaseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InternshipPhaseResponse getPhaseById(Integer id) {
        InternshipPhase phase = phaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đợt thực tập này!"));
        return phaseMapper.toResponse(phase);
    }

    @Override
    public InternshipPhaseResponse createPhase(InternshipPhaseRequest request) {
        validateDates(request);

        InternshipPhase phase = InternshipPhase.builder()
                .phaseName(request.getPhaseName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .description(request.getDescription())
                .build();

        return phaseMapper.toResponse(phaseRepository.save(phase));
    }

    @Override
    public InternshipPhaseResponse updatePhase(Integer id, InternshipPhaseRequest request) {
        validateDates(request);

        InternshipPhase phase = phaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đợt thực tập này!"));

        phase.setPhaseName(request.getPhaseName());
        phase.setStartDate(request.getStartDate());
        phase.setEndDate(request.getEndDate());
        phase.setDescription(request.getDescription());

        return phaseMapper.toResponse(phaseRepository.save(phase));
    }

    @Override
    public void deletePhase(Integer id) {
        if (!phaseRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy đợt thực tập này!");
        }
        phaseRepository.deleteById(id);
    }
}
