package com.hielectro.welpair.mypage.controller;


import com.hielectro.welpair.mypage.model.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage/*")
public class MypageController {

    //로그인 체크할것!

    private final MypageService mypageService;

    @Autowired
    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }


    //회원정보 페이지로 이동시 본인(비밀번호) 인증 페이지
    @GetMapping("/checkPwd")
    public String checkPwd() {

        return "consumer/mypage/myinfo1";
    }

    //회원정보수정페이지
    @GetMapping("/editMyInfo")
    public String editMyInfo() {

        return "consumer/mypage/myinfo2";
    }

    //배송지목록 html css 재작성필요
    @GetMapping("/myAddress")
    public String myAddress() {

        return "consumer/mypage/myaddress1";
    }

    //배송지등록페이지
    @GetMapping("/myAddress2")
    public String myAddress2() {

        return "consumer/mypage/myaddress2";
    }

    //위시리스트 css수정필요
    @GetMapping("/wishlist")
    public String wishlist() {

        return "consumer/mypage/wishlist";
    }

    //포인트 css일부수정필요
    @GetMapping("/myPoint")
    public String myPoint() {

        return "consumer/mypage/mypoint";
    }


    //내가쓴글-문의목록
    //*****리뷰 페이지 html 재작성해야함
    @GetMapping("/myPost")
    public String myPost() {

        return "consumer/mypage/myqna";
    }







}
