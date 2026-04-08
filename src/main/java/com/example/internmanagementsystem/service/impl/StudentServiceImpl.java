package com.example.internmanagementsystem.service.impl;

import com.example.internmanagementsystem.config.UserContextHelper;
import com.example.internmanagementsystem.dto.request.StudentRequest;
import com.example.internmanagementsystem.dto.request.StudentUpdateRequest;
import com.example.internmanagementsystem.dto.response.StudentResponse;
import com.example.internmanagementsystem.entity.Student;
import com.example.internmanagementsystem.entity.User;
import com.example.internmanagementsystem.enums.Role;
import com.example.internmanagementsystem.mapper.StudentMapper;
import com.example.internmanagementsystem.repository.StudentRepository;
import com.example.internmanagementsystem.repository.UserRepository;
import com.example.internmanagementsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserContextHelper userContextHelper;

    @Override
    public List<StudentResponse> getAllStudents() {
        User currentUser = userContextHelper.getCurrentUser();

        if (currentUser.getRole() == Role.ADMIN) {
            return studentRepository.findAllByUserRole(Role.STUDENT).stream()
                    .map(studentMapper::toResponse).collect(Collectors.toList());
        } else if (currentUser.getRole() == Role.MENTOR) {
            return studentRepository.findStudentsAssignedToMentor(currentUser.getUserId())
                    .stream().map(studentMapper::toResponse).collect(Collectors.toList());
        }
        throw new RuntimeException("Quyền truy cập bị từ chối!");
    }

    @Override
    public StudentResponse getStudentById(Integer id) {
        User currentUser = userContextHelper.getCurrentUser();
        if (currentUser.getRole() == Role.STUDENT && !currentUser.getUserId().equals(id)) {
            throw new RuntimeException("Lỗi bảo mật: Bạn chỉ được phép xem thông tin của chính mình!");
        }
        if(currentUser.getRole() == Role.MENTOR){
            Student student = studentRepository.findStudentsAssignedToMentorByStudentId(currentUser.getUserId(), id).orElseThrow(() -> new RuntimeException("Lỗi bảo mật: Sinh viên này không thuộc quyền quản lý của bạn!"));
            return studentMapper.toResponse(student);
        }
        else {
            Student student = studentRepository.findStudentByStudentIdAndUserRole(id, Role.STUDENT)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin sinh viên!"));
            return studentMapper.toResponse(student);
        }
    }

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User với ID này!"));

        if (user.getRole() != Role.STUDENT) {
            throw new RuntimeException("Lỗi: Tài khoản này không có Role là STUDENT!");
        }
        if (studentRepository.existsById(user.getUserId())) {
            throw new RuntimeException("Lỗi: Tài khoản này đã có hồ sơ Sinh viên rồi!");
        }

        Student student = Student.builder()
                .user(user)
                .studentCode(request.getStudentCode())
                .major(request.getMajor())
                .className(request.getClassName())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .build();

        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Override
    public StudentResponse updateStudent(Integer id, StudentUpdateRequest request) {
        User currentUser = userContextHelper.getCurrentUser();

        if (currentUser.getRole() == Role.STUDENT && !currentUser.getUserId().equals(id)) {
            throw new RuntimeException("Lỗi bảo mật: Bạn chỉ được phép cập nhật thông tin của chính mình!");
        }

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin sinh viên!"));

        student.setStudentCode(request.getStudentCode());
        student.setMajor(request.getMajor());
        student.setClassName(request.getClassName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setAddress(request.getAddress());

        return studentMapper.toResponse(studentRepository.save(student));
    }
}
