package com.example.internmanagementsystem.exeption;

import com.example.internmanagementsystem.dto.response.ApiResponse;
import com.example.internmanagementsystem.dto.response.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ValidationError> validationErrors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> new ValidationError(
                        ((FieldError) error).getField(),
                        error.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        log.error("Lỗi đầu vào: {}", validationErrors);

        ApiResponse<Object> apiResponse = ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                "Dũ liệu đâu vào không hợp lệ",
                validationErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeExceptions(RuntimeException ex) {
        log.error("Lỗi nghiệp vụ: {}", ex.getMessage());

        ApiResponse<Object> apiResponse = ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                "Lỗi thực thi",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalExceptions(Exception ex) {
        log.error("Lỗi hệ thống: ", ex);

        ApiResponse<Object> apiResponse = ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Lỗi hệ thống",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Từ chối truy cập: {}", ex.getMessage());

        ApiResponse<Object> apiResponse = ApiResponse.error(
                HttpStatus.FORBIDDEN.value(),
                "Bạn không có quyền truy cập",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error("Không tìm thấy tên người dùng {}", ex.getMessage());

        ApiResponse<Object> apiResponse = ApiResponse.error(
                HttpStatus.NOT_FOUND.value(),
                "Không tìm thấy tên người dùng",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Sai mật khẩu {}", ex.getMessage());

        ApiResponse<Object> apiResponse = ApiResponse.error(
                HttpStatus.UNAUTHORIZED.value(),
                "Sai mật khẩu",
                "Lỗi xác thực mật khẩu!"
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Sai định dạng trong body! {}",  ex.getMessage());
        ApiResponse<Object> apiResponse = ApiResponse.error(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Sai định dạng trong body!",
                "Truyền sai định dạng trong body(có thể là Id)!"
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("Chuyền sai định dạng id trên url!");
        ApiResponse<Object> apiResponse = ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                "Chuyền sai định dạng id trên url!",
                "Id trên url không phải số"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(EnumConstantNotPresentException.class)
    public ResponseEntity<ApiResponse<Object>> handleEnumConstantNotPresentException(EnumConstantNotPresentException ex) {
        log.error("Chuyền sai định dạng tham số");
        ApiResponse<Object> apiResponse = ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                "Chuyền sai định dạng tham số",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
