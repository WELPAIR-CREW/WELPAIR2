package com.hielectro.welpair.post.controller;


import com.hielectro.welpair.post.model.dto.AdminBoardDTO;
import com.hielectro.welpair.post.model.service.AdminBoardService;
import com.hielectro.welpair.post.model.service.AdminBoardServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/post")
public class PostManagerController {

    private final AdminBoardService adminBoardService;

    private final AdminBoardServiceImpl Service;



    public PostManagerController(AdminBoardService adminBoardService, AdminBoardServiceImpl service) {
        this.adminBoardService = adminBoardService;

        Service = service;
    }


    @GetMapping("/admin/board_write")
    public String PostWrite(Model model){
        return "/post/admin/board_write";
    }

    @PostMapping("/admin/board_write")
    public String PostSave(@ModelAttribute AdminBoardDTO adminBoard){
        System.out.println(adminBoard);
        Service.PostSave(adminBoard);
        System.out.println(adminBoard);

        return "redirect:/post/admin/board_write";
    }




}


