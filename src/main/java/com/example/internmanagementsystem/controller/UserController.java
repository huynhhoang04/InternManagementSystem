package com.example.internmanagementsystem.controller;

import com.example.internmanagementsystem.dto.request.UserRequest;
import com.example.internmanagementsystem.dto.request.UserRoleRequest;
import com.example.internmanagementsystem.dto.request.UserStatusRequest;
import com.example.internmanagementsystem.dto.request.UserUpdateRequest;
import com.example.internmanagementsystem.dto.response.ApiResponse;
import com.example.internmanagementsystem.dto.response.UserResponse;
import com.example.internmanagementsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success(userService.getAllUsers()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(ApiResponse.success(userService.getUserById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(userService.createUser(request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateUser(id, request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserStatus(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UserStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateUserStatus(id, request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/role")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserRole(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UserRoleRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateUserRole(id, request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
