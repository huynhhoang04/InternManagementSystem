package com.example.internmanagementsystem.repository;

import com.example.internmanagementsystem.entity.Mentor;
import com.example.internmanagementsystem.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MentorRepository extends JpaRepository<Mentor, Integer> {
    List<Mentor> findAllByUserRole(Role mentor);
    Optional<Mentor> findByUserRoleAndMentorId(Role mentor, Integer mentorId);
}
