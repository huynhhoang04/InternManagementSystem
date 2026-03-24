package com.example.internmanagementsystem.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserProfileResponse {
    // thông tin user
    private Integer userId;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String role;

    // thông tin student
    private String studentCode;
    private String major;
    private String className;
    private LocalDate dateOfBirth;
    private String address;

    // thông tin mentor
    private String department;
    private String academicRank;
}
