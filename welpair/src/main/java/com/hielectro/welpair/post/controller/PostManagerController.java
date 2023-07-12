package com.hielectro.welpair.post.controller;


import com.hielectro.welpair.post.model.dto.AdminBoardDTO;
import com.hielectro.welpair.post.model.service.AdminBoardService;
import com.hielectro.welpair.post.model.service.AdminBoardServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/post/*")
public class PostManagerController {

    private final AdminBoardService adminBoardService;

    private final AdminBoardServiceImpl adminBoardServiceImpl;



    public PostManagerController(AdminBoardService adminBoardService, AdminBoardServiceImpl adminBoardServiceImpl) {
        this.adminBoardService = adminBoardService;


        this.adminBoardServiceImpl = adminBoardServiceImpl;
    }


    @GetMapping("/admin/board_write")
    public String PostWrite(Model model){
        return "/post/admin/board_write";
    }

    @PostMapping("/admin/board_write")
    public String PostSave(@ModelAttribute AdminBoardDTO adminBoardDTO, RedirectAttributes rttr) throws BoardException {

        System.out.println("===========================================CHECK==========================="+adminBoardDTO);
        adminBoardServiceImpl.PostSave(adminBoardDTO);
        rttr.addFlashAttribute("message","게시글등록성공");

        return "redirect:/post/admin/board_write";
    }




}


