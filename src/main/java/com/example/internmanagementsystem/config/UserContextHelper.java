package com.example.internmanagementsystem.config;

import com.example.internmanagementsystem.entity.User;
import com.example.internmanagementsystem.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserContextHelper {
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!= null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getUser();
        }
        throw new RuntimeException("Lỗi xác thực: Không tìm thấy phiên đăng nhập!");
    }
}
