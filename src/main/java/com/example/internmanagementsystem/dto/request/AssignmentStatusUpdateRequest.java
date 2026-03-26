package com.example.internmanagementsystem.dto.request;

import com.example.internmanagementsystem.enums.AssignmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentStatusUpdateRequest {
    @NotNull(message = "Trạng thái không được để trống")
    private AssignmentStatus status;
}
