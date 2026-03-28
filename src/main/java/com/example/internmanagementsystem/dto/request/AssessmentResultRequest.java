package com.example.internmanagementsystem.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AssessmentResultRequest {
    @NotNull(message = "ID Phân công không được để trống")
    private Integer assignmentId;

    @NotNull(message = "ID Đợt đánh giá không được để trống")
    private Integer roundId;

    @NotNull(message = "ID Tiêu chí không được để trống")
    private Integer criterionId;

    @NotNull(message = "Điểm số không được để trống")
    @Min(value = 0, message = "Điểm số không được âm")
    private BigDecimal score;

    private String comments;
}
