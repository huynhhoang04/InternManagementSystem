package com.example.internmanagementsystem.service.impl;

import com.example.internmanagementsystem.dto.request.MentorRequest;
import com.example.internmanagementsystem.dto.request.MentorUpdateRequest;
import com.example.internmanagementsystem.dto.response.MentorResponse;
import com.example.internmanagementsystem.entity.Mentor;
import com.example.internmanagementsystem.entity.User;
import com.example.internmanagementsystem.enums.Role;
import com.example.internmanagementsystem.mapper.MentorMapper;
import com.example.internmanagementsystem.repository.MentorRepository;
import com.example.internmanagementsystem.repository.UserRepository;
import com.example.internmanagementsystem.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {
    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MentorMapper mentorMapper;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Lỗi xác thực: Không tìm thấy tài khoản!"));
    }

    @Override
    public List<MentorResponse> getAllMentors() {
        User user = getCurrentUser();
        if (user.getRole().equals(Role.ADMIN)) {
            return mentorRepository.findAllByUserRole(Role.MENTOR).stream()
                    .map(mentorMapper::toResponse)
                    .collect(Collectors.toList());
        }
        if (user.getRole().equals(Role.STUDENT)) {
            return mentorRepository.findAllByUserRole(Role.MENTOR).stream()
                    .map(mentor -> MentorResponse.builder()
                            .fullName(mentor.getUser().getFullName())
                            .department(mentor.getDepartment())
                            .build()
                    )
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public MentorResponse getMentorById(Integer id) {
        User currentUser = getCurrentUser();
        if (currentUser.getRole() == Role.MENTOR && !currentUser.getUserId().equals(id)) {
            throw new RuntimeException("Lỗi bảo mật: Bạn chỉ được phép xem thông tin của chính mình!");
        }

        Mentor mentor = mentorRepository.findByUserRoleAndMentorId(Role.MENTOR, id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin giảng viên hướng dẫn!"));
        return mentorMapper.toResponse(mentor) ;
    }

    @Override
    public MentorResponse createMentor(MentorRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User với ID này!"));

        if (user.getRole() != Role.MENTOR) {
            throw new RuntimeException("Lỗi: Tài khoản này không có Role là MENTOR!");
        }
        if (mentorRepository.existsById(user.getUserId())) {
            throw new RuntimeException("Lỗi: Tài khoản này đã có hồ sơ Mentor rồi!");
        }

        Mentor mentor = Mentor.builder()
                .user(user)
                .department(request.getDepartment())
                .academicRank(request.getAcademicRank())
                .build();

        return mentorMapper.toResponse(mentorRepository.save(mentor));
    }

    @Override
    public MentorResponse updateMentor(Integer id, MentorUpdateRequest request) {
        User currentUser = getCurrentUser();

        if (currentUser.getRole() == Role.MENTOR && !currentUser.getUserId().equals(id)) {
            throw new RuntimeException("Lỗi bảo mật: Bạn chỉ được phép xem thông tin của chính mình!");
        }

        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin giảng viên hướng dẫn!"));

        mentor.setDepartment(request.getDepartment());
        mentor.setAcademicRank(request.getAcademicRank());

        return mentorMapper.toResponse(mentorRepository.save(mentor));
    }
}
