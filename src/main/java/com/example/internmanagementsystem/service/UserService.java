package com.example.internmanagementsystem.service;

import com.example.internmanagementsystem.dto.request.UserRequest;
import com.example.internmanagementsystem.dto.request.UserRoleRequest;
import com.example.internmanagementsystem.dto.request.UserStatusRequest;
import com.example.internmanagementsystem.dto.request.UserUpdateRequest;
import com.example.internmanagementsystem.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Integer id);
    UserResponse createUser(UserRequest request);

    UserResponse updateUser(Integer id, UserUpdateRequest request);
    UserResponse updateUserStatus(Integer id, UserStatusRequest request);
    UserResponse updateUserRole(Integer id, UserRoleRequest request);
    void deleteUser(Integer id);
}
