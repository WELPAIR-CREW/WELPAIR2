package com.hielectro.welpair.member.controller;

import com.hielectro.welpair.common.Pagination;
import com.hielectro.welpair.member.model.dto.DeptDTO;
import com.hielectro.welpair.member.model.dto.EmployeeDTO;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.dto.ReqDTO;
import com.hielectro.welpair.member.model.service.MemberService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

import java.util.*;

@Controller
@RequestMapping("/member/*")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    //1. 회원조회 - 회원목록
    @GetMapping("/member-view")
    public ModelAndView getMemberList(@RequestParam(required = false) String searchCondition, @RequestParam(required = false) String searchValue
            , @RequestParam(value="currentPage", defaultValue="1") int currentPage, ModelAndView model
            , @RequestParam(value="type", required = false) String isExpired) {
        //int currentPage: URL로 전달되는 현재 페이지 번호로 URL에 제공되지 않으면 1로 설정됨)
        //String isExpired: URL로 전달되는 값...버튼을 눌렀을때 추가됨되도록 타임리프의 속성으로 추가돼있음

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
        System.out.println(isExpired);
//        int totalMemberCount = memberService.totalMemberCount(searchMap); //총 항목 수(검색 조건 적용)
        int listLength = memberService.totalMemberCount(searchMap); //총 항목 수(검색 조건 적용)
        int expiredMemberCount = memberService.expiredMemberCount(searchMap); //퇴사한 직원 수
        int itemsPerPage = 10; //페이지당 항목 수
        int displayPageCount = 5; //표시할 페이지 번호 수

        int totalPages = 0;
//        totalPages = (int) Math.ceil((double) totalMemberCount / itemsPerPage);
        totalPages = (int) Math.ceil((double) listLength / itemsPerPage);


        SelectCriteria selectCriteria = null;

        if(searchCondition != null && !"".equals(searchCondition)) { //검색어 있을때
//            selectCriteria = Pagenation.getSelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount, searchCondition, searchValue, isExpired);
            selectCriteria = Pagenation.getSelectCriteria(currentPage, listLength, itemsPerPage, displayPageCount, searchCondition, searchValue, isExpired);
        } else { //검색어 없을때
//            selectCriteria = Pagenation.getSelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount, isExpired);
            selectCriteria = Pagenation.getSelectCriteria(currentPage, listLength, itemsPerPage, displayPageCount, isExpired);
        }

        List<MemberDTO> memberList = memberService.getMemberList(selectCriteria);

        //쿼리결과를 받아 DTO리스트에 담고 모델에 추가
        model.addObject("memberList", memberList);
        model.addObject("selectCriteria", selectCriteria);

        //전체 회원 수, 퇴사한 회원 수
//        model.addObject("totalMemberCount", totalMemberCount);
        model.addObject("totalMemberCount", listLength);
        model.addObject("expiredMemberCount", expiredMemberCount);

        //퇴사한 회원만 가져오는지 확인
        System.out.println(memberList);

        //@페이지묶음 - 지금은 전체 페이지를 다 가져오도록하는 코드임
        List<Integer> pageNumList = new ArrayList<>();
        for (int i=1; i<=totalPages; i++) {
            pageNumList.add(i);
        }
        model.addObject("pageNumbList", pageNumList);

        model.setViewName("admin/member/member-view");

        return model;
    }

    @PostMapping("/deleteMember")
    public String deleteMember(@RequestParam ArrayList<String> empNos) throws DeleteMemberException {

        System.out.println("체크박스 체크된 사번의 배열이 잘 들어오는지 확인 : " + empNos);

        memberService.deleteMember(empNos);

        return "redirect:/member/member-view"; //삭제 후 회원조회 페이지로 리다이렉트
    }


    //2. 회원등록 - 직원목록
    @GetMapping("/regist")
    public ModelAndView getEmployeeList(@RequestParam(required = false) String searchCondition, @RequestParam(required = false) String searchValue
            , @RequestParam(value="currentPage", defaultValue="1") int currentPage, ModelAndView model) {


        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        int listLength = memberService.totalEmployeeCount(searchMap); //총 항목 수(검색 조건 적용)
        int itemsPerPage = 10; //페이지당 항목 수
        int displayPageCount = 5; //표시할 페이지 번호 수

        int totalPages = 0;
        totalPages = (int) Math.ceil((double) listLength / itemsPerPage);

        SelectCriteria selectCriteria = null;

        if(searchCondition != null && !"".equals(searchCondition)) { //검색어 있을때
            selectCriteria = Pagenation.getSelectCriteria(currentPage, listLength, itemsPerPage, displayPageCount, searchCondition, searchValue, null);
        } else { //검색어 없을때
            selectCriteria = Pagenation.getSelectCriteria(currentPage, listLength, itemsPerPage, displayPageCount, null);
        }

        //...필터, 검색 관련 내용 넣기...

        List<EmployeeDTO> employeeList = memberService.getEmployeeList(selectCriteria);

        model.addObject("employeeList", employeeList);
        model.addObject("selectCriteria", selectCriteria);
        model.setViewName("admin/member/member-regist1");
        return model;
    }


    @GetMapping("/reqList")
    public ModelAndView getReqList(@RequestParam(value="currentPage", defaultValue="1") int currentPage, ModelAndView model) {

        System.out.println("-------------가입요청리스트");

        //신규 가입요청 수
        int reqJoinCount = memberService.reqJoinCount();
        System.out.println("가입요청 수 : " + reqJoinCount);
        model.addObject("reqJoinCount", reqJoinCount);

        //신규 가입요청 목록
        List<ReqDTO> reqList = memberService.reqList();
        model.addObject("reqList", reqList);

        model.setViewName("admin/member/member-permission");
        return model;
    }


    @PostMapping("/registPage") //@ResponseBody를 붙이는건 ajax일때만 해당
    public ModelAndView registPage(@ModelAttribute EmployeeDTO employee) {
                                    //form.submit()해서 보내면 @ModelAttribute이고 body에 담아서 보내면 @RequestBody임

        ModelAndView model = new ModelAndView("admin/member/member-regist2");
        System.out.println("employeeDTO로 들어왔는지 확인 : " + employee);
        model.addObject("employee", employee);

        return model;
    }

    @PostMapping("/registMember")
    public String registMember(@ModelAttribute MemberDTO member) throws RegistMemberException {

        System.out.println("회원등록 submit 후 들어오는지 확인---------------------");
        System.out.println("회원등록 member = " + member); //여기서 memPwd=null로 들어오는것이 문제
        memberService.registMember(member);
        return "redirect:/member/regist"; //등록 후 회원등록(목록) 페이지로 리다이렉트
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
