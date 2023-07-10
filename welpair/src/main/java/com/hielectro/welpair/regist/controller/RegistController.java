package com.hielectro.welpair.regist.controller;

import com.hielectro.welpair.regist.model.dto.RegistDTO;
import com.hielectro.welpair.regist.model.service.RegistService;
import com.hielectro.welpair.regist.model.service.RegistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/member")
public class RegistController {


    private final PasswordEncoder passwordEncoder;

    private final RegistService registService;

    private final RegistServiceImpl service;

    public RegistController(PasswordEncoder passwordEncoder, RegistService registService, RegistServiceImpl service) {
        this.passwordEncoder = passwordEncoder;
        this.registService = registService;
        this.service = service;
    }
    // 회원가입 창

    /* 회원가입창을 띄웁시다. */
    @GetMapping("registForm")
    public String registForm(Model model){
        return "member/registForm";
    }


    @PostMapping("registForm")
    public String registSave(@ModelAttribute RegistDTO regist, RedirectAttributes rttr) throws MemberRegistException {

        System.out.println(regist);
        regist.setMemPwd(passwordEncoder.encode(regist.getMemPwd()));  // 패스워드 인코딩됨
        System.out.println(regist);
        service.registSave(regist);

        rttr.addFlashAttribute("message", "회원 가입에 성공하였습니다.");



        return "redirect:/member/login";
    }





//    @PostMapping("registForm")   // 값확인
//    public String registSave(@RequestParam("empName") String empName,
//                             @RequestParam("empNo") String empNo,
//                             @RequestParam("memPwd") String memPwd){
//        System.out.println("empName = " + empName + ", empNo = " + empNo + ", memPwd = " + memPwd);
//
//        return null;
//
//    }








    @PostMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("empNo") String empNo) throws MemberRegistException {

        System.out.println(empNo);
        int cnt = registService.idCheck(empNo);


        return cnt;

    }


}
