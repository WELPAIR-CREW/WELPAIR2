package com.hielectro.welpair.post.admin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostManagerController {

    @GetMapping("/admin/board_write")
    public String PostWrite(Model model){
        return "/post/admin/board_write";
    }
}
