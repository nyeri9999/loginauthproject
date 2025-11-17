package com.example.backend.domain.board.dto;

import com.example.backend.domain.board.entity.BoardEntity;

public record BoardResponseDTO(
        Long id,
        String title,
        String Content,
        String writerNickName
) {
    public static BoardResponseDTO fromEntity(BoardEntity board) {
        String nickname = board.getUser().getNickname();

        return new BoardResponseDTO(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                nickname
        );
    }

}
