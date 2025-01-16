package com.min.app01.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.min.app01.dto.BoardDto;

@Mapper // 나는 mybatis mapper 입니다.
public interface IBoardMapper {
   
  // mapper.xml을 사용하지 않는 방식
  @Select("SELECT NOW()")
  String now();
  
  @Select("SELECT board_id, title, contents, create_dt FROM tbl_board ORDER BY board_id DESC")
  List<BoardDto> selectBoardList();
  
  @Select("SELECT board_id, title, contents, create_dt FROM tbl_board WHERE board_id = #{boardId}")
  BoardDto selectBoardById(@Param("boardId") int boardId);
  
  @Insert("INSERT INTO tbl_board VALUES (null, #{title}, #{contents}, NOW())")
  int insertBoard(BoardDto boardDto);
  
  @Update("UPDATE tbl_board SET title = #{title}, contents = #{contents}        WHERE boardId = #{boardId}")
  int updateBoard(BoardDto boardDto);
  
  @Delete("DELETE FROM tbl_board WHERE board_id = #{boardId}")
  int deleteBoard(@Param("boardId") int boardId);
  
}



