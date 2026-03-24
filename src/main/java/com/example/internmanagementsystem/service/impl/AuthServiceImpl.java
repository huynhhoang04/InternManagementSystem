package com.example.internmanagementsystem.service.impl;

import com.example.internmanagementsystem.dto.request.LoginRequest;
import com.example.internmanagementsystem.dto.response.JwtAuthResponse;
import com.example.internmanagementsystem.dto.response.UserProfileResponse;
import com.example.internmanagementsystem.entity.User;
import com.example.internmanagementsystem.repository.MentorRepository;
import com.example.internmanagementsystem.repository.StudentRepository;
import com.example.internmanagementsystem.repository.UserRepository;
import com.example.internmanagementsystem.security.CustomUserDetails;
import com.example.internmanagementsystem.security.JwtTokenProvider;
import com.example.internmanagementsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MentorRepository mentorRepository;

    @Override
    public JwtAuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String jwt = tokenProvider.generateToken(authentication);

        return new JwtAuthResponse(
                jwt,
                userDetails.getUsername(),
                userDetails.getUser().getRole().name()
        );
    }

    @Override
    public UserProfileResponse getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("No user found with username " + currentUsername));

        UserProfileResponse.UserProfileResponseBuilder responseBuilder = UserProfileResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name());

        if (user.getRole() == com.example.internmanagementsystem.enums.Role.STUDENT) {
            studentRepository.findById(user.getUserId()).ifPresent(student -> {
                responseBuilder.studentCode(student.getStudentCode())
                        .major(student.getMajor())
                        .className(student.getClassName())
                        .dateOfBirth(student.getDateOfBirth())
                        .address(student.getAddress());
            });
        }
        else if (user.getRole() == com.example.internmanagementsystem.enums.Role.MENTOR) {
            mentorRepository.findById(user.getUserId()).ifPresent(mentor -> {
                responseBuilder.department(mentor.getDepartment())
                        .academicRank(mentor.getAcademicRank());
            });
        }

        return responseBuilder.build();
    }
}
