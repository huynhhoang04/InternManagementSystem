package com.example.internmanagementsystem.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AssessmentRoundRequest {
    @NotNull(message = "ID của Đợt thực tập (Phase) không được để trống")
    private Integer phaseId;

    @NotBlank(message = "Tên đợt đánh giá không được để trống")
    private String roundName;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDate endDate;

    @NotEmpty(message = "Phải có ít nhất 1 tiêu chí đánh giá")
    @Valid
    private List<CriterionWeightRequest> criteria;
}
