package com.hielectro.welpair.regist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class RegistController {


    // 회원가입 창

    /* 회원가입창을 띄웁시다. */
    @GetMapping("registForm")
    public String registForm(Model model){
        return "member/registForm";
    }

    @PostMapping("registForm")
    public String registSave(@RequestParam("empNo") String empNo,
                             @RequestParam("memPwd") String memPwd){
        System.out.println("empNo = " + empNo + ", memPwd = " + memPwd);

        return null;

    }
}
