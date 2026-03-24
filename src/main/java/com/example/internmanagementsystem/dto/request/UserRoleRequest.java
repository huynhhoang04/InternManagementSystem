package com.example.internmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleRequest {
    @NotBlank(message = "Role không được để trống")
    private String role;
}
