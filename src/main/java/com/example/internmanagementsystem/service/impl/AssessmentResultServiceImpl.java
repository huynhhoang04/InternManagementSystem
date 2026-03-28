package com.example.internmanagementsystem.service.impl;

import com.example.internmanagementsystem.dto.request.AssessmentResultRequest;
import com.example.internmanagementsystem.dto.request.AssessmentResultUpdateRequest;
import com.example.internmanagementsystem.dto.response.AssessmentResultResponse;
import com.example.internmanagementsystem.entity.*;
import com.example.internmanagementsystem.enums.Role;
import com.example.internmanagementsystem.mapper.AssessmentResultMapper;
import com.example.internmanagementsystem.repository.*;
import com.example.internmanagementsystem.service.AssessmentResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessmentResultServiceImpl implements AssessmentResultService {
    @Autowired
    private AssessmentResultRepository resultRepository;
    @Autowired
    private InternshipAssignmentRepository assignmentRepository;
    @Autowired
    private AssessmentRoundRepository roundRepository;
    @Autowired
    private EvaluationCriterionRepository criterionRepository;
    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssessmentResultMapper mapper;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Lỗi xác thực!"));
    }

    @Override
    public List<AssessmentResultResponse> getResults(Integer assignmentId) {
        User currentUser = getCurrentUser();
        List<AssessmentResult> results;

        if (currentUser.getRole() == Role.ADMIN) {
            results = (assignmentId != null)
                    ? resultRepository.findByAssignment_AssignmentId(assignmentId)
                    : resultRepository.findAll();
        } else if (currentUser.getRole() == Role.MENTOR) {
            results = resultRepository.findByAssignment_Mentor_MentorId(currentUser.getUserId());
            if (assignmentId != null) {
                results = results.stream().filter(r -> r.getAssignment().getAssignmentId().equals(assignmentId)).collect(Collectors.toList());
            }
        } else if (currentUser.getRole() == Role.STUDENT) {
            results = resultRepository.findByAssignment_Student_StudentId(currentUser.getUserId());
            if (assignmentId != null) {
                results = results.stream().filter(r -> r.getAssignment().getAssignmentId().equals(assignmentId)).collect(Collectors.toList());
            }
        } else {
            throw new RuntimeException("Quyền truy cập bị từ chối!");
        }

        return results.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public AssessmentResultResponse createResult(AssessmentResultRequest request) {
        User currentUser = getCurrentUser();

        Mentor currentMentor = mentorRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy hồ sơ Giảng viên của bạn!"));

        InternshipAssignment assignment = assignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin Phân công!"));

        if (!assignment.getMentor().getMentorId().equals(currentUser.getUserId())) {
            throw new RuntimeException("Lỗi: Bạn chỉ được phép chấm điểm cho sinh viên mà bạn được phân công hướng dẫn!");
        }

        AssessmentRound round = roundRepository.findById(request.getRoundId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Đợt đánh giá!"));

        EvaluationCriterion criterion = criterionRepository.findById(request.getCriterionId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Tiêu chí đánh giá!"));

        if (resultRepository.existsByAssignment_AssignmentIdAndRound_RoundIdAndCriterion_CriterionId(
                request.getAssignmentId(), request.getRoundId(), request.getCriterionId())) {
            throw new RuntimeException("Tiêu chí này đã được chấm điểm trong đợt này rồi! Vui lòng sử dụng chức năng Cập nhật.");
        }

        AssessmentResult result = AssessmentResult.builder()
                .assignment(assignment)
                .round(round)
                .criterion(criterion)
                .score(request.getScore())
                .comments(request.getComments())
                .evaluatedBy(currentMentor)
                .build();

        return mapper.toResponse(resultRepository.save(result));
    }

    @Override
    public AssessmentResultResponse updateResult(Integer id, AssessmentResultUpdateRequest request) {
        User currentUser = getCurrentUser();

        AssessmentResult result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kết quả đánh giá!"));
        if (!result.getAssignment().getMentor().getMentorId().equals(currentUser.getUserId())) {
            throw new RuntimeException("Lỗi: Bạn chỉ được phép cập nhật điểm cho sinh viên của chính mình!");
        }

        result.setScore(request.getScore());
        result.setComments(request.getComments());

        return mapper.toResponse(resultRepository.save(result));
    }
}
