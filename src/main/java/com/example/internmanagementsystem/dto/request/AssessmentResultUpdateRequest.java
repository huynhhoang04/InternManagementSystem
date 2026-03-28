package com.example.internmanagementsystem.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AssessmentResultUpdateRequest {
    @NotNull(message = "Điểm số không được để trống")
    @Min(value = 0, message = "Điểm số không được âm")
    private BigDecimal score;

    private String comments;
}
