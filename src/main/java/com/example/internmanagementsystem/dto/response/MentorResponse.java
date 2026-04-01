package com.example.internmanagementsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MentorResponse {
    private Integer mentorId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String department;
    private String academicRank;

    public MentorResponse() {
    }
}
