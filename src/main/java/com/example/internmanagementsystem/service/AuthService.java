package com.example.internmanagementsystem.service;

import com.example.internmanagementsystem.dto.request.LoginRequest;
import com.example.internmanagementsystem.dto.response.JwtAuthResponse;
import com.example.internmanagementsystem.dto.response.UserProfileResponse;

public interface AuthService {
    JwtAuthResponse login(LoginRequest loginRequest);
    UserProfileResponse getCurrentUserProfile();
}
