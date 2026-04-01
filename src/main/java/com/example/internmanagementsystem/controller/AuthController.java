package com.example.internmanagementsystem.controller;

import com.example.internmanagementsystem.dto.request.LoginRequest;
import com.example.internmanagementsystem.dto.response.ApiResponse;
import com.example.internmanagementsystem.dto.response.JwtAuthResponse;
import com.example.internmanagementsystem.dto.response.UserProfileResponse;
import com.example.internmanagementsystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtAuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtAuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'STUDENT')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getCurrentUser() {
        UserProfileResponse profile = authService.getCurrentUserProfile();
        return ResponseEntity.ok(ApiResponse.success(profile));
    }
}
