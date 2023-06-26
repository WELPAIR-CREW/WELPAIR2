package com.hielectro.welpair.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MemberController {

    @RequestMapping(value = "/admin/member/view")
    public String memberView() {
        return "admin/member/member-view";

    }

//    @RequestMapping(value = "/admin/member/regist")
//    public String memberRegist() {
//        return "admin/member/member-regist1";
//
//    }

    @RequestMapping(value = "/admin/member/permission")
    public String memberPermission() {
        return "admin/member/member-permission";
    }
}
