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
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    //1. 회원조회 - 회원목록
    @GetMapping("/member-view")
    public ModelAndView getMemberList(HttpServletRequest request, @RequestParam(required = false) String searchCondition, @RequestParam(required = false) String searchValue
            , @RequestParam(value="currentPage", defaultValue="1") int currentPage, ModelAndView model) {
                                //int currentPage: URL로 전달되는 현재 페이지 번호로 URL에 제공되지 않으면 1로 설정됨)

        System.out.println("test----------------- ");
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        int totalMemberCount = memberService.totalMemberCount(searchMap); //총 항목 수(검색 조건 적용)
        int itemsPerPage = 10; //페이지당 항목 수
        int displayPageCount = 5; //표시할 페이지 번호 수


        SelectCriteria selectCriteria = null;

        if(searchCondition != null && !"".equals(searchCondition)) {
            selectCriteria = Pagenation.getSelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount, searchCondition, searchValue);
        } else {
            selectCriteria = Pagenation.getSelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount);
        }

        List<MemberDTO> memberList = memberService.getMemberList(selectCriteria);

        //쿼리결과를 받아 DTO리스트에 담고 모델에 추가
        model.addObject("memberList", memberList);
        model.addObject("selectCriteria", selectCriteria);
        model.setViewName("admin/member/member-view");
        return model;
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

}
