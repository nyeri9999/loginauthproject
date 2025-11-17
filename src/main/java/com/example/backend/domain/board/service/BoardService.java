package com.example.backend.domain.board.service;

import com.example.backend.domain.board.dto.BoardRequestDTO;
import com.example.backend.domain.board.dto.BoardResponseDTO;
import com.example.backend.domain.board.entity.BoardEntity;
import com.example.backend.domain.board.repository.BoardRepository;
import com.example.backend.domain.user.entity.UserEntity;
import com.example.backend.domain.user.entity.UserRoleType;
import com.example.backend.domain.user.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private BoardRepository boardRepository;
    private UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    // 게시글 생성
    @Transactional
    public BoardResponseDTO createOneBoard(BoardRequestDTO dto) {
        // user name 얻어오기
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity currentUser = userRepository.findByUsernameAndIsLock(currentUsername, false)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다." + currentUsername));

        BoardEntity boardEntity = BoardEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(currentUser)
                .build();

        // 엔티티에다가 위에 빌더로 쌓은거 저장
        BoardEntity savedBoard = boardRepository.save(boardEntity);
        // 리턴은 리퀘스트 형태로 보내야되서 변환을 따로 함.
        return BoardResponseDTO.fromEntity(savedBoard);
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public BoardResponseDTO readOneBoard(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));

        return BoardResponseDTO.fromEntity(boardEntity);
    }

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDTO> readAllBoard() {
        List<BoardEntity> list = boardRepository.findAllWithUser();

        return list.stream()
                .map(BoardResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 게시글 수정
    @Transactional
    public BoardResponseDTO updateOneBoard(Long id, BoardRequestDTO dto) {
        BoardEntity boardEntity = findBoardAndCheckPermission(id);
        boardEntity.update(dto.getTitle(), dto.getContent());
        return BoardResponseDTO.fromEntity(boardEntity);
    }

    @Transactional
    public void deleteOneBoard(Long id) {
        BoardEntity boardEntity = findBoardAndCheckPermission(id);
        boardRepository.delete(boardEntity);
    }

    // 검증 메서드
    private BoardEntity findBoardAndCheckPermission(Long boardId) {
        // 1. 게시글 조회 (없으면 예외)
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다: " + boardId));

        // 2. 현재 인증 정보 획득
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AccessDeniedException("인증 정보가 없습니다.");
        }

        String currentUsername = authentication.getName();

        // 3. 작성자 닉네임 획득
        String authorUsername = board.getUser().getUsername();

        // 4. '관리자' 여부 확인
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + UserRoleType.ADMIN.name()));

        // 5. (핵심) '본인'도 아니고 '관리자'도 아니면, 예외 발생
        if (!currentUsername.equals(authorUsername) && !isAdmin) {
            throw new AccessDeniedException("이 게시글에 대한 수정/삭제 권한이 없습니다.");
        }

        // 6. 검증 통과 시, 게시글 엔티티 반환
        return board;
    }

}
