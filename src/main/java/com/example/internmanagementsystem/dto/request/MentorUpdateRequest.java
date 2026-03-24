package com.example.internmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorUpdateRequest {
    @NotBlank(message = "Khoa/Bộ môn không được để trống")
    private String department;

    @NotBlank(message = "Học hàm/Học vị không được để trống")
    private String academicRank;
}
