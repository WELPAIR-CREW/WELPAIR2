package com.hielectro.welpair.member.controller;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    //1. 회원조회 - 회원목록
    @GetMapping("member-view")
    public String getMemberList(Model model) {

        List<MemberDTO> memberList = memberService.getMemberList();
        model.addAttribute("memberList", memberList);

        return "admin/member/member-view";
    }


    @RequestMapping(value = "/regist")
    public String memberRegist() {
        return "admin/member/member-regist1";

    }

    @RequestMapping(value = "/permission")
    public String memberPermission() {
        return "admin/member/member-permission";
    }
}
