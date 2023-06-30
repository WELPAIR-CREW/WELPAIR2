package com.hielectro.welpair.member.controller;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class MemberController {

    /* 로그인 페이지 */
    @GetMapping("login")
    public String login(Model model){

        return "/member/login";
    }

    @PostMapping("login")
    public String loginForm(@RequestParam("empNo") String empNo,
                            @RequestParam("memPwd") String emePwd){
        System.out.println("empNo = " + empNo + ", emePwd = " + emePwd);

        return null;
    }



    /* 회원가입 페이지 */
    @GetMapping("regist")
    public String registForm(Model model){

        return "/member/regist";
    }

    @PostMapping("regist")
    public String registSave(@RequestParam("memName") String memName,
                            @RequestParam("empNo") String empNo,
                            @RequestParam("memPwd") String memPwd){
        System.out.println("MemberController.registSave");
        System.out.println("memName : " + memName);
        System.out.println("memNo : " + empNo);
        System.out.println("pwd : " + memPwd);
        return null;
    }





    @GetMapping("findid")
    public String findId(Model model){
        return "/member/findid";
    }

    @GetMapping("findpwd")
    public String findPwd(Model model){
        return "/member/findpwd";
    }

}
