package com.hielectro.welpair.member.controller;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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
    public String getMemberList(@RequestParam(name="currentPage", defaultValue="1") int currentPage, Model model) {
                                //int currentPage: URL로 전달되는 현재 페이지 번호로 URL에 제공되지 않으면 1로 설정됨)

        //1-4.
        int itemsPerPage = 10; //페이지당 항목 수
        List<MemberDTO> memberList = memberService.getMemberList(currentPage, itemsPerPage);

        //1-1. 쿼리결과를 받아 DTO리스트에 담고 모델에 추가
//        List<MemberDTO> memberList = memberService.getMemberList();
        model.addAttribute("memberList", memberList);

        //1-2. 페이징처리를 위해 현재 페이지 번호를 모델에 추가
        int totalPages = getTotalPages(memberList);


        //1-3. 페이지번호들을 동적으로 보여주기위해 추가(5개씩)------------------------
        int displayPageCount = 5; //원하는 표시 페이지 수
        int startPage = Math.max(1, currentPage - displayPageCount/2);
        int endPage = Math.min(startPage + displayPageCount - 1, totalPages);
        List<Integer> pages = new ArrayList<>(); //페이지번호들의 리스트
        for(int page = startPage; page <= endPage; page++) {
            pages.add(page);
        }
        model.addAttribute("pages", pages);
        //----------------------------------------------------------------

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);

        return "admin/member/member-view";
    }


    @GetMapping("regist")
    public String getEmpListForRegist() {
        return "admin/member/member-regist1";

    }

    @GetMapping("permission")
    public String getReqList() {
        return "admin/member/member-permission";
    }


    @GetMapping("givePoint")
    public String getMemberListForPoint() {
        return "admin/member/member-givePoint";
    }


    @GetMapping("givePointHistory")
    public String getPointHistoryList() {
        return "admin/member/member-givePointHistory1";
    }



    //1-2. 페이징처리를 위해 전체페이지 수를 계산하는 메서드
    private int getTotalPages(List<MemberDTO> memberList) {
        int totalItems = memberList.size(); //총 항목 수
        int itemsPerPage = 10; //페이지당 항목 수
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage); //총 페이지 수
        System.out.println("총 페이지 수 : " + totalPages);
        return totalPages;
    }





}
