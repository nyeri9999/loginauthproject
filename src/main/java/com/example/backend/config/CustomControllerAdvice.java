package com.example.backend.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 이 어노테이션을 통해서 모든 애플리케이션에서 @RestController에서 발생한 예외를 얘가 가로채서 공통 응답을 내려줄 수 있도록 한다.
@RestControllerAdvice
public class CustomControllerAdvice {

    // 이 예외는 인증은 되었으나 권한이 없는(Forbidden) 사용자가 리소스 접근시 발생하는 예외.
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("접근 권한이 없습니다.");
    }

    // 이 메서드는 위에서 정의한 예외 외에도 대부분 오류를 잡기 위한 핸들러이다. 400 bad request 응답을 반환함.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청입니다.");
    }

}