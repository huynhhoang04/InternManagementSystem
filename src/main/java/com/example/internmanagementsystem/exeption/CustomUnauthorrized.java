package com.example.internmanagementsystem.exeption;

import com.example.internmanagementsystem.dto.response.ApiResponse;
import com.example.internmanagementsystem.dto.response.JwtAuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

@Component
@Slf4j
public class CustomUnauthorrized implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Chưa xác thực");
        response.setContentType("application/json");
        response.setStatus(401);
        ApiResponse<JwtAuthResponse> apiResponse = ApiResponse.error(
                HttpStatus.UNAUTHORIZED.value(),
                "Bạn cần đăng nhập để thực hiện hành động này",
                authException.getMessage()
        );
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.writeValue(response.getOutputStream(), apiResponse);
        response.getOutputStream().flush();
    }
}
