package com.example.internmanagementsystem.service.impl;

import com.example.internmanagementsystem.config.UserContextHelper;
import com.example.internmanagementsystem.dto.request.AssignmentRequest;
import com.example.internmanagementsystem.dto.request.AssignmentStatusUpdateRequest;
import com.example.internmanagementsystem.dto.response.AssignmentResponse;
import com.example.internmanagementsystem.entity.*;
import com.example.internmanagementsystem.enums.AssignmentStatus;
import com.example.internmanagementsystem.enums.Role;
import com.example.internmanagementsystem.mapper.AssignmentMapper;
import com.example.internmanagementsystem.repository.*;
import com.example.internmanagementsystem.service.InternshipAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternshipAssignmentServiceImpl implements InternshipAssignmentService {
    @Autowired
    private InternshipAssignmentRepository assignmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private InternshipPhaseRepository phaseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssignmentMapper mapper;
    @Autowired
    private UserContextHelper userContextHelper;


    @Override
    public List<AssignmentResponse> getAllAssignments(Integer userId) {
        User currentUser = userContextHelper.getCurrentUser();

        if (currentUser.getRole() == Role.ADMIN) {
            if (userId != null) {
                    return assignmentRepository.findByStudent_StudentIdOrMentor_MentorId(userId, userId).stream()
                            .map(mapper::toResponse).collect(Collectors.toList());
            }
            return assignmentRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
        } else if (currentUser.getRole() == Role.MENTOR) {
            return assignmentRepository.findByMentor_MentorId(currentUser.getUserId()).stream()
                    .map(mapper::toResponse).collect(Collectors.toList());
        } else if (currentUser.getRole() == Role.STUDENT) {
            return assignmentRepository.findByStudent_StudentId(currentUser.getUserId()).stream()
                    .map(mapper::toResponse).collect(Collectors.toList());
        }
        throw new RuntimeException("Quyền truy cập bị từ chối!");
    }

    @Override
    public AssignmentResponse getAssignmentById(Integer id) {
        InternshipAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi phân công này!"));

        User currentUser = userContextHelper.getCurrentUser();
        if (currentUser.getRole() == Role.MENTOR && !assignment.getMentor().getMentorId().equals(currentUser.getUserId())) {
            throw new RuntimeException("Bạn không có quyền xem phân công của giảng viên khác!");
        }
        if (currentUser.getRole() == Role.STUDENT && !assignment.getStudent().getStudentId().equals(currentUser.getUserId())) {
            throw new RuntimeException("Bạn không có quyền xem phân công của sinh viên khác!");
        }

        return mapper.toResponse(assignment);
    }

    @Override
    public AssignmentResponse createAssignment(AssignmentRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Sinh viên!"));
        Mentor mentor = mentorRepository.findById(request.getMentorId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Giảng viên!"));
        InternshipPhase phase = phaseRepository.findById(request.getPhaseId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Đợt thực tập!"));

        if (assignmentRepository.existsByStudent_StudentIdAndPhase_PhaseId(request.getStudentId(), request.getPhaseId())) {
            throw new RuntimeException("Sinh viên này đã được phân công trong đợt thực tập này rồi!");
        }

        InternshipAssignment assignment = InternshipAssignment.builder()
                .student(student)
                .mentor(mentor)
                .phase(phase)
                .status(AssignmentStatus.PENDING)
                .build();

        return mapper.toResponse(assignmentRepository.save(assignment));
    }

    @Override
    public AssignmentResponse updateStatus(Integer id, AssignmentStatusUpdateRequest request) {
        InternshipAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi phân công này!"));

        assignment.setStatus(AssignmentStatus.valueOf(String.valueOf(request.getStatus())));
        return mapper.toResponse(assignmentRepository.save(assignment));
    }
}
