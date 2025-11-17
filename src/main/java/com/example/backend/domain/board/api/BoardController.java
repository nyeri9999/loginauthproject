package com.example.backend.domain.board.api;

import com.example.backend.domain.board.dto.BoardRequestDTO;
import com.example.backend.domain.board.dto.BoardResponseDTO;
import com.example.backend.domain.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 글 작성
    @PostMapping
    public ResponseEntity<BoardResponseDTO> createBoard(
            @Validated @RequestBody BoardRequestDTO dto
    ) {
        BoardResponseDTO responseDTO = boardService.createOneBoard(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

    // 글 목록 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> readAllBoard() {
        List<BoardResponseDTO> boardList = boardService.readAllBoard();
        return ResponseEntity.ok(boardList);
    }

    // 특정 글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> readBoardById(@PathVariable("id") Long id) {
        BoardResponseDTO board = boardService.readOneBoard(id);
        return ResponseEntity.ok(board);
    }

    // 글 수정
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> updateBoard(
        @PathVariable("id") Long id,
        @Validated @RequestBody BoardRequestDTO dto
    ) {
        BoardResponseDTO updatedBoard = boardService.updateOneBoard(id, dto);
        return ResponseEntity.ok(updatedBoard);
    }

    // 글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteOneBoard(id);
        return ResponseEntity.noContent().build();
    }

}
