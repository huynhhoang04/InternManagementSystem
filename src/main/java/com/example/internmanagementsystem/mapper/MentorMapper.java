package com.example.internmanagementsystem.mapper;


import com.example.internmanagementsystem.dto.response.MentorResponse;
import com.example.internmanagementsystem.entity.Mentor;
import com.example.internmanagementsystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MentorMapper {
    public MentorResponse toResponse(Mentor mentor) {
        if (mentor == null) return null;
        User user = mentor.getUser();
        return MentorResponse.builder()
                .mentorId(mentor.getMentorId())
                .fullName(user != null ? user.getFullName() : null)
                .email(user != null ? user.getEmail() : null)
                .phoneNumber(user != null ? user.getPhoneNumber() : null)
                .department(mentor.getDepartment())
                .academicRank(mentor.getAcademicRank())
                .build();
    }
}
