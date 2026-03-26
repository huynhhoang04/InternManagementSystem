package com.example.internmanagementsystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentRequest {
    @NotNull(message = "ID Sinh viên không được để trống")
    private Integer studentId;

    @NotNull(message = "ID Giảng viên hướng dẫn không được để trống")
    private Integer mentorId;

    @NotNull(message = "ID Đợt thực tập không được để trống")
    private Integer phaseId;
}
