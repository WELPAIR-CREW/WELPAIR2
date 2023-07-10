package com.hielectro.welpair.noticeboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping("board_Ask")
    public String boardAsk(Model model){
        return "/board/board_Ask";
    }




}
