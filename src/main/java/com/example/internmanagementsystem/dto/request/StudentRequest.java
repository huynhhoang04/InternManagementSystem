package com.example.internmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentRequest {
    @NotNull(message = "ID của User (Tài khoản) không được để trống")
    private Integer userId;

    @NotBlank(message = "Mã sinh viên không được để trống")
    private String studentCode;

    private String major;
    private String className;
    private LocalDate dateOfBirth;
    private String address;
}
