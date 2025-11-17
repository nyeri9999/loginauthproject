package com.example.backend.domain.jwt.dto;

// record 클래스는 final 클래스로, 정의된 필드에 대하여 자동 생성해준다.
// 해당 클래스의 경우 access token과 refresh token 이 두가지를 한 쌍으로 묶어 전달하는 dto이다.
public record JWTResponseDTO(String accessToken, String refreshToken) {
}