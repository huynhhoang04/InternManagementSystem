package com.example.internmanagementsystem.service;

import com.example.internmanagementsystem.dto.request.MentorRequest;
import com.example.internmanagementsystem.dto.request.MentorUpdateRequest;
import com.example.internmanagementsystem.dto.response.MentorResponse;

import java.util.List;

public interface MentorService {
    List<MentorResponse> getAllMentors();
    MentorResponse getMentorById(Integer id);
    MentorResponse createMentor(MentorRequest request);
    MentorResponse updateMentor(Integer id, MentorUpdateRequest request);
}
