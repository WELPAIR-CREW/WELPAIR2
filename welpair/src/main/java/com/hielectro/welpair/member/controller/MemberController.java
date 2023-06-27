package com.hielectro.welpair.member.controller;

import com.hielectro.welpair.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/member")
public class MemberController {

    private final MemberService memberService;


    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("memberview")
    public String memberView() {
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
