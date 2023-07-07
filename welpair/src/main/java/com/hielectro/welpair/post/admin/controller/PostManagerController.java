package com.hielectro.welpair.post.admin.controller;


import com.hielectro.welpair.post.admin.model.dao.AdminBoardDAO;
import com.hielectro.welpair.post.admin.model.dto.AdminBoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostManagerController {

    @GetMapping("/admin/board_write")
    public String PostWrite(Model model){
        return "/post/admin/board_write";
    }

    @PostMapping("/admin/board-write")
    public String BoardSave(@ModelAttribute AdminBoardDTO adminBoardDTO){

        System.out.println(adminBoardDTO);

        return null;

    }







}


