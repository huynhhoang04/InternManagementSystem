package com.example.internmanagementsystem.service;

import com.example.internmanagementsystem.dto.request.InternshipPhaseRequest;
import com.example.internmanagementsystem.dto.response.InternshipPhaseResponse;

import java.util.List;

public interface InternshipPhaseService {
    List<InternshipPhaseResponse> getAllPhases();

    InternshipPhaseResponse getPhaseById(Integer id);

    InternshipPhaseResponse createPhase(InternshipPhaseRequest request);

    InternshipPhaseResponse updatePhase(Integer id, InternshipPhaseRequest request);

    void deletePhase(Integer id);
}
