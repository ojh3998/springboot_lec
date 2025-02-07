package com.min.app03.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.min.app03.dto.BoardDto;
import com.min.app03.mapper.IBoardMapper;
import com.min.app03.util.PageUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements IBoardService {
  
  private final IBoardMapper boardMapper;
  private final PageUtil pageUtil;

  @Override
  public Map<String, Object> getBoardList(HttpServletRequest request) {
    
    // 페이징 퍼리를 위한 파라미터 page, display
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1")); 
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("20"));
    
    // 페이징 처리를 위한 게시글 개수 count
    int count = boardMapper.selectBoardCount();
    
    // 페이징 처리에 필요한 모든 변수 처리하기
    pageUtil.setPaging(page, display, count);
    
    //offset
    int offset = pageUtil.getOffset();
    
    // 정렬을 위한 파라미터 sort
    Optional<String> optSort = Optional.ofNullable(request.getParameter("sort"));
    String sort = optSort.orElse("DESC");
    
    // 게시글 목록 가져오기 (전다 : offset, display, sort를 저장한 MAP)
    List<BoardDto> boardList = boardMapper.selectBoardList(Map.of("offset", offset, "display", display, "sort", sort));
    
    // 페이징 가져오기 (전달 : 게시글 목록을 처리하는 주소 , 정렬방식/목록 개수/ 검색 같은 추가 파라미터들)
    String paging = pageUtil.getPaging("/list.do", Map.of("display", display, "sort", sort));
    
    
    //결과 반환
    return Map.of("boardList", boardList, "count", count, "offset", offset, "paging", paging);
  }

  @Override
  public BoardDto getBoardById(int boardId) {
    return boardMapper.selectBordById(boardId);
  }
   

  @Override
  public String registBoard(BoardDto boardDto) {
    return boardMapper.insertBoard(boardDto) == 1 ? "등록성공" : "등록 실패"; 
  }
    

  @Override
  public String modifyBoard(BoardDto boardDto) {
    return boardMapper.updateBoard(boardDto) == 1 ? "수정성공" : "수정실패";
   
  }

  @Override
  public String removeBoard(int boardId) {
    return boardMapper.deleteBoard(boardId) == 1? "삭제성공" : "삭제실패";
    
  }

}
