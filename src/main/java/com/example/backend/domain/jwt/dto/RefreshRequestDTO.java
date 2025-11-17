package com.example.backend.domain.jwt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// access token을 재발급할 때 요청하는 요청 전용 dto이다.
public class RefreshRequestDTO {

    @NotBlank // null 값이나 공백이 전송되면 유효성 검사 실패함.
    private String refreshToken;
}