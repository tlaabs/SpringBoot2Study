package com.web.service;

import com.web.domain.Board;
import com.web.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    /*
    page, size
    페이징 처리된 게시글 리스트 반환
    page : http://localhost:8080/board/list?page=-7와 같이 음수값이 들어가면 0으로 설정
    size : 한번에 보여주는 페이지 수
     */

    public Page<Board> findBoardList(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() -1, pageable.getPageSize());
        //finAll에서 실제로 모든 글을 다 가져오지는 않겠지? Pageable통해 필요한 것만 가져오라고 예상.(나중에 알아보자)
        return boardRepository.findAll(pageable);
    }

    public Board findBoardByIdx(Long idx){
        return boardRepository.findById(idx).orElse(new Board());
    }
}
