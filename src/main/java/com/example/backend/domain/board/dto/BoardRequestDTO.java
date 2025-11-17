package com.example.backend.domain.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDTO {

    // notblank로 빈값 못들어오도록 제한, 최소 길이/최대길이 입력 제한 추가
    // 이런식으로 예외를 방어해두면 프론트단에서 혹시나 null 값인 채로 api 호출시 발생할 예외를 막을 수 있음.
    // BoardController에서 request dto 가져올떄 @Validated라는 어노테이션이 추가되면 이 기능이 자동으로 동작한다.
    @NotBlank(message = "제목은 필수 입력 항목입니다. 제목을 입력해 주십시오")
    @Size(min=1, max=100, message = "제목을 1자 이상 100자 이하로 입력해주세요")
    public String title;

    @NotBlank(message = "내용은 필수 입력 항목입니다. 내용을 입력해 주십시오.")
    public String content;
}
