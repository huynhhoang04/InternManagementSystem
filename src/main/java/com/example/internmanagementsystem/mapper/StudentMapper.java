package com.example.internmanagementsystem.mapper;

import com.example.internmanagementsystem.dto.response.StudentResponse;
import com.example.internmanagementsystem.entity.Student;
import com.example.internmanagementsystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentResponse toResponse(Student student) {
        if (student == null) return null;
        User user = student.getUser();
        return StudentResponse.builder()
                .studentId(student.getStudentId())
                .studentCode(student.getStudentCode())
                .fullName(user != null ? user.getFullName() : null)
                .email(user != null ? user.getEmail() : null)
                .major(student.getMajor())
                .className(student.getClassName())
                .dateOfBirth(student.getDateOfBirth())
                .address(student.getAddress())
                .build();
    }
}
