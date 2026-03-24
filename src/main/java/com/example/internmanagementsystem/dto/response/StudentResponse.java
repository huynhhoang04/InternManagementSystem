package com.example.internmanagementsystem.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class StudentResponse {
    private Integer studentId;
    private String studentCode;
    private String fullName;
    private String email;
    private String major;
    private String className;
    private LocalDate dateOfBirth;
    private String address;
}
