package com.example.internmanagementsystem.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EvaluationCriterionRequest {
    @NotBlank(message = "Tên tiêu chí không được để trống")
    private String criterionName;

    @NotNull(message = "Điểm tối đa/Trọng số không được để trống")
    @Min(value = 0, message = "Điểm tối đa phải lớn hơn hoặc bằng 0")
    private BigDecimal maxScore;

    private String description;
}
