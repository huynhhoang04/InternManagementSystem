package com.example.internmanagementsystem.mapper;

import com.example.internmanagementsystem.dto.response.StudentResponse;
import com.example.internmanagementsystem.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentResponse toResponse(Student student) {
        if (student == null) return null;
        return StudentResponse.builder()
                .studentId(student.getStudentId())
                .studentCode(student.getStudentCode())
                .fullName(student.getUser().getFullName())
                .email(student.getUser().getEmail())
                .major(student.getMajor())
                .className(student.getClassName())
                .dateOfBirth(student.getDateOfBirth())
                .address(student.getAddress())
                .build();
    }
}
