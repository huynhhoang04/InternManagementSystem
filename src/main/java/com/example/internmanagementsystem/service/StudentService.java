package com.example.internmanagementsystem.service;

import com.example.internmanagementsystem.dto.request.StudentRequest;
import com.example.internmanagementsystem.dto.request.StudentUpdateRequest;
import com.example.internmanagementsystem.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getAllStudents();
    StudentResponse getStudentById(Integer id);
    StudentResponse createStudent(StudentRequest request);

    StudentResponse updateStudent(Integer id, StudentUpdateRequest request);
}
