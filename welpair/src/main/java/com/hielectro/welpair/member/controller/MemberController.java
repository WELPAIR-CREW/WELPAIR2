package com.hielectro.welpair.member.controller;

import com.hielectro.welpair.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/member")
public class MemberController {

    private final MemberService memberService;


    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    //회원조회-전체회원수, 상태변경수
    @GetMapping("memberview")
    @ResponseBody
    public String memberView(Model model) {

        model.addAttribute("totalMember", memberService.countAllMember());
        model.addAttribute("changedMember", memberService.countChangeMember());

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
