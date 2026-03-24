package com.example.internmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorRequest {
    @NotNull(message = "ID của User không được để trống")
    private Integer userId;

    private String department;
    private String academicRank;
}
