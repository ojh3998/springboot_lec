package com.min.app03.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.min.app03.dto.BoardDto;
import com.min.app03.service.IBoardService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {
  
  private final IBoardService boardService;
  
  @GetMapping("/")
  public String home() {
    return "home"; // SpringResouceTemplateResolver (templateResolver) 에 의해서
                   // prefix="WEB-INF/templates"
                   // suffix=".html"
  }
  
  @GetMapping("/list.do")
  public String list(HttpServletRequest request, Model model) {
    Map<String, Object> map = boardService.getBoardList(request);
    model.addAttribute("boardList", map.get("boardList"));
    model.addAttribute("count", map.get("count"));
    model.addAttribute("offset", map.get("offset"));
    model.addAttribute("paging", map.get("paging"));
    return "list";
  }
    
  @GetMapping("/detail.do")
  public String detail(@RequestParam("boardId") int boardId, Model model) {
    model.addAttribute("board", boardService.getBoardById(boardId));
    return "detail";
  }
  
  @GetMapping("/write.do")
  public String write() {
    return "write";
  }
  
  @PostMapping("/regist.do")
  public String regist(BoardDto boardDto, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("msg", boardService.registBoard(boardDto));
    return "redirect:/list.do";
  }
  
  @GetMapping("/edit.do")
  public String edit(int boardId, Model model) {
    model.addAttribute("board", boardService.getBoardById(boardId));
    return "edit";
  }
  
  @PostMapping("/modify.do")
  public String modify(BoardDto boardDto, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("msg", boardService.modifyBoard(boardDto));
    return "redirect:/detail.do?boardId=" + boardDto.getBoardId();
  }
  
  @GetMapping("/remove.do")
  public String remove(int boardId, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("msg", boardService.removeBoard(boardId));
    return "redirect:/list.do";
  }


}
   
