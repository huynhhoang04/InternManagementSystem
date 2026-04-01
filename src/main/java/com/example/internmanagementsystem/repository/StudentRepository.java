package com.example.internmanagementsystem.repository;

import com.example.internmanagementsystem.entity.Student;
import com.example.internmanagementsystem.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT DISTINCT a.student FROM InternshipAssignment a WHERE a.mentor.user.userId = :mentorId")
    List<Student> findStudentsAssignedToMentor(@Param("mentorId") Integer mentorId);

    List<Student> findAllByUserRole(Role role);

    Optional<Student> findStudentByStudentIdAndUserRole(Role role, Integer id);
}
