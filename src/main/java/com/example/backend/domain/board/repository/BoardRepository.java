package com.example.backend.domain.board.repository;

import com.example.backend.domain.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository <BoardEntity, Long> {

    @Query("select b from BoardEntity b join fetch b.user")
    List<BoardEntity> findAllWithUser();
}
