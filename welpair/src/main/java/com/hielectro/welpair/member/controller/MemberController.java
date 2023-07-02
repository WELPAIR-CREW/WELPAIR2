package com.hielectro.welpair.member.controller;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
            , @RequestParam(value="currentPage", defaultValue="1") int currentPage, ModelAndView model
            , @RequestParam(value="type", required = false) String isExpired) {
                                //int currentPage: URL로 전달되는 현재 페이지 번호로 URL에 제공되지 않으면 1로 설정됨)
                                //String isExpired: URL로 전달되는 값...버튼을 눌렀을때 추가됨되도록 타임리프의 속성으로 추가돼있음

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
        System.out.println(isExpired);
        int totalMemberCount = memberService.totalMemberCount(searchMap); //총 항목 수(검색 조건 적용)
        int expiredMemberCount = memberService.expiredMemberCount(searchMap); //퇴사한 직원 수
        int itemsPerPage = 10; //페이지당 항목 수
        int displayPageCount = 5; //표시할 페이지 번호 수

        int totalPages = 0;
        totalPages = (int) Math.ceil((double) totalMemberCount / itemsPerPage);


        SelectCriteria selectCriteria = null;

// 원래코드
//        if(searchCondition != null && !"".equals(searchCondition)) {
//            selectCriteria = Pagenation.getSelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount, searchCondition, searchValue, isExpired);
//        } else {
//            selectCriteria = Pagenation.getSelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount, isExpired);
//        }

        if(isExpired != null) {

            if(searchCondition != null && !"".equals(searchCondition)) {
                selectCriteria = Pagenation.getSelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount, searchCondition, searchValue, isExpired);
            } else {
                selectCriteria = Pagenation.getSelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount, isExpired);
            }

        } else {

            if(searchCondition != null && !"".equals(searchCondition)) {
                selectCriteria = Pagenation.getSelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount, searchCondition, searchValue);
            } else {
                selectCriteria = Pagenation.getSelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount);
            }

        }
        System.out.println(selectCriteria.getIsExpired());


        List<MemberDTO> memberList = memberService.getMemberList(selectCriteria);

        //쿼리결과를 받아 DTO리스트에 담고 모델에 추가
        model.addObject("memberList", memberList);
        model.addObject("selectCriteria", selectCriteria);

        //전체 회원 수, 퇴사한 회원 수
        model.addObject("totalMemberCount", totalMemberCount);
        model.addObject("expiredMemberCount", expiredMemberCount);

        //퇴사한 회원만 가져오는지 확인
        System.out.println(memberList);

        //페이지묶음 - 지금은 전체 페이지를 다 가져오도록하는 코드임
        List<Integer> pageNumList = new ArrayList<>();
        for (int i=1; i<=totalPages; i++) {
            pageNumList.add(i);
        }
        model.addObject("pageNumbList", pageNumList);

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







//    로그인 창

    /* 로그인창을 띄웁시다. */
    @GetMapping("login")
    public String login(Model model){

        return "member/login";

    }

    @PostMapping("login")
    public String loginForm(@RequestParam("empNo") String empNo,
                            @RequestParam("memPwd") String memPwd){

        System.out.println("empNo = " + empNo + ", memPwd = " + memPwd);
        return null;

    }





// 회원가입 창

    /* 회원가입창을 띄웁시다. */
    @GetMapping("registForm")
    public String registForm(Model model){
        return "member/registForm";
    }

    @PostMapping("registForm")
    public String registSave(@RequestParam("empName") String empName,
                            @RequestParam("empNo") String empNo,
                            @RequestParam("memPwd") String memPwd){

        System.out.println("memName = " + empName + ", empNo = " + empNo + ", memPwd = " + memPwd);

        return null;
    }


}













