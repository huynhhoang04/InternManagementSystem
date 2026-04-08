package com.example.internmanagementsystem.service.impl;

import com.example.internmanagementsystem.config.UserContextHelper;
import com.example.internmanagementsystem.dto.request.AssessmentResultRequest;
import com.example.internmanagementsystem.dto.request.AssessmentResultUpdateRequest;
import com.example.internmanagementsystem.dto.response.AssessmentResultResponse;
import com.example.internmanagementsystem.entity.*;
import com.example.internmanagementsystem.enums.Role;
import com.example.internmanagementsystem.mapper.AssessmentResultMapper;
import com.example.internmanagementsystem.repository.*;
import com.example.internmanagementsystem.service.AssessmentResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
    @Autowired
    private UserContextHelper userContextHelper;

    @Override
    public List<AssessmentResultResponse> getResults(Integer assignmentId, Integer userId) {
        User currentUser = userContextHelper.getCurrentUser();
        User paramuser = null;
        Integer studentId = null;
        Integer mentorId = null;

        if(userId != null) {paramuser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Không tìm thấy id người dùng"));}


        if (currentUser.getRole() == Role.STUDENT) {
            studentId = currentUser.getUserId();
            mentorId = userId;
        } else if (currentUser.getRole() == Role.MENTOR) {
            mentorId = currentUser.getUserId();
            studentId = userId;
        } else if (currentUser.getRole() == Role.ADMIN) {
            if (paramuser.getRole() == (Role.STUDENT)) {
                studentId = userId;
            }
            else if (paramuser.getRole() == (Role.MENTOR)) {
                mentorId = userId;
            }
            else {throw new RuntimeException("Id phải là của mentor hoặc học viên");}
        }

        List<AssessmentResult> results = resultRepository.findFilteredResults(assignmentId, studentId, mentorId);

        return results.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AssessmentResultResponse createResult(AssessmentResultRequest request) {
        User currentUser = userContextHelper.getCurrentUser();

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
        User currentUser = userContextHelper.getCurrentUser();

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
