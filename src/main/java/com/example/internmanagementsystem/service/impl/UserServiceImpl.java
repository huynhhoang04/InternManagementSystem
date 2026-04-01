package com.example.internmanagementsystem.service.impl;

import com.example.internmanagementsystem.dto.request.UserRequest;
import com.example.internmanagementsystem.dto.request.UserRoleRequest;
import com.example.internmanagementsystem.dto.request.UserStatusRequest;
import com.example.internmanagementsystem.dto.request.UserUpdateRequest;
import com.example.internmanagementsystem.dto.response.UserResponse;
import com.example.internmanagementsystem.entity.User;
import com.example.internmanagementsystem.enums.Role;
import com.example.internmanagementsystem.mapper.UserMapper;
import com.example.internmanagementsystem.repository.MentorRepository;
import com.example.internmanagementsystem.repository.StudentRepository;
import com.example.internmanagementsystem.repository.UserRepository;
import com.example.internmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username đã tồn tại!");
        }

        User user = User.builder()
                .username(request.getUsername())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role(Role.valueOf(request.getRole()))
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Integer id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUserStatus(Integer id, UserStatusRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));

        user.setIsActive(request.getIsActive());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUserRole(Integer id, UserRoleRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));

        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Lỗi bảo mật: Không được phép thay đổi quyền của ADMIN!");
        }
        user.setRole(Role.valueOf(request.getRole()));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));

        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Không thể xóa tài khoản của ADMIN!");
        }
        userRepository.deleteById(id);
    }
}
