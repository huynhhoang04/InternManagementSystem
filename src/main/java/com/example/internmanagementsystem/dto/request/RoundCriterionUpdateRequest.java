package com.example.internmanagementsystem.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RoundCriterionUpdateRequest {
    @NotNull(message = "Trọng số không được để trống")
    @Min(value = 0, message = "Trọng số phải lớn hơn hoặc bằng 0")
    private BigDecimal weight;
}