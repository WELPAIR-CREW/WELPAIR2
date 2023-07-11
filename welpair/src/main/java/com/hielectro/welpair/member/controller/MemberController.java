package com.hielectro.welpair.member.controller;

import com.hielectro.welpair.member.model.dto.EmployeeDTO;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.dto.PointHistoryDTO;
import com.hielectro.welpair.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder; //회원등록할때 비밀번호 암호화 필요

    @Autowired
    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;

    }





    //1. 회원조회 - 회원목록

    //새로운 페이징 적용하기
    @GetMapping("/member-view")
    public ModelAndView getMemberList(@RequestParam(defaultValue="1") int page, ModelAndView model
                                    , @RequestParam(value="type", required = false) String isExpired) {
        //int page: URL로 전달되는 현재 페이지 번호로 URL에 제공되지 않으면 1로 설정됨)
        //String isExpired: URL로 전달되는 값...버튼을 눌렀을때 추가됨되도록 타임리프의 속성으로 추가돼있음

        int pageSize = 10;
        int totalCnt = memberService.totalMemberCount();
        System.out.println("db에서 조회한 총 회원항목수 : " + totalCnt);
        PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);
        model.addObject("totalCnt", totalCnt);

        Map<String, Integer> map = new HashMap<>();
        map.put("startRow", pageHandler.getStartRow());
        map.put("endRow", pageHandler.getEndRow());

        //쿼리스트링으로 type을 받았다면 그 값을 맵에 추가로 담는다
        if (isExpired != null && isExpired.equals("Y")) {
            map.put("isExpired", 1);
        } else {
            map.put("isExpired", 2);
        }
        System.out.println(isExpired);

        //회원목록 받아오기
        List<MemberDTO> memberList = memberService.getMemberList(map);
        model.addObject("memberList", memberList);
        model.addObject("pageHandler", pageHandler);


        System.out.println(memberList);

        //전체 회원수와 퇴사한 회원수
//        int totalMemberCount = memberService.totalMemberCount();
//        model.addObject("totalMemberCount", totalMemberCount);

        int expiredMemberCount = memberService.expiredMemberCount();
        model.addObject("expiredMemberCount", expiredMemberCount);

        model.setViewName("admin/member/member-view");

        return model;
    }



    //기존 페이징
//    @GetMapping("/member-view")
//    public ModelAndView getMemberList(@RequestParam(required = false) String searchCondition, @RequestParam(required = false) String searchValue
//            , @RequestParam(value="currentPage", defaultValue="1") int currentPage, ModelAndView model
//            , @RequestParam(value="type", required = false) String isExpired) {
//        //int currentPage: URL로 전달되는 현재 페이지 번호로 URL에 제공되지 않으면 1로 설정됨)
//        //String isExpired: URL로 전달되는 값...버튼을 눌렀을때 추가됨되도록 타임리프의 속성으로 추가돼있음
//
//        Map<String, String> searchMap = new HashMap<>();
//        searchMap.put("searchCondition", searchCondition);
//        searchMap.put("searchValue", searchValue);
//        System.out.println(isExpired);
////        int totalMemberCount = memberService.totalMemberCount(searchMap); //총 항목 수(검색 조건 적용)
//        int listLength = memberService.totalMemberCount(searchMap); //총 항목 수(검색 조건 적용)
//        int expiredMemberCount = memberService.expiredMemberCount(searchMap); //퇴사한 직원 수
//        int itemsPerPage = 10; //페이지당 항목 수
//        int displayPageCount = 5; //표시할 페이지 번호 수
//
//        int totalPages = 0;
////        totalPages = (int) Math.ceil((double) totalMemberCount / itemsPerPage);
//        totalPages = (int) Math.ceil((double) listLength / itemsPerPage);
//
//
//        SelectCriteria selectCriteria = null;
//
//        if(searchCondition != null && !"".equals(searchCondition)) { //검색어 있을때
////            selectCriteria = Pagenation.getSelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount, searchCondition, searchValue, isExpired);
//            selectCriteria = Pagenation.getSelectCriteria(currentPage, listLength, itemsPerPage, displayPageCount, searchCondition, searchValue, isExpired);
//        } else { //검색어 없을때
////            selectCriteria = Pagenation.getSelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount, isExpired);
//            selectCriteria = Pagenation.getSelectCriteria(currentPage, listLength, itemsPerPage, displayPageCount, isExpired);
//        }
//
//        List<MemberDTO> memberList = memberService.getMemberList(selectCriteria);
//
//        //쿼리결과를 받아 DTO리스트에 담고 모델에 추가
//        model.addObject("memberList", memberList);
//        model.addObject("selectCriteria", selectCriteria);
//
//        //전체 회원 수, 퇴사한 회원 수
////        model.addObject("totalMemberCount", totalMemberCount);
//        model.addObject("totalMemberCount", listLength);
//        model.addObject("expiredMemberCount", expiredMemberCount);
//
//        //퇴사한 회원만 가져오는지 확인
//        System.out.println(memberList);
//
//        //@페이지묶음 - 지금은 전체 페이지를 다 가져오도록하는 코드임
//        List<Integer> pageNumList = new ArrayList<>();
//        for (int i=1; i<=totalPages; i++) {
//            pageNumList.add(i);
//        }
//        model.addObject("pageNumbList", pageNumList);
//
//        model.setViewName("admin/member/member-view");
//
//        return model;
//    }

    @PostMapping("/deleteMember")
    public String deleteMember(@RequestParam ArrayList<String> empNos, HttpServletRequest request) throws DeleteMemberException {

        System.out.println("체크박스 체크된 사번의 배열이 잘 들어오는지 확인 : " + empNos);

        memberService.deleteMember(empNos);

        //url주소를 추출하여 이를 기반으로 리다이렉트 주소 분기
        String root = request.getHeader("Referer");
        System.out.println("이전페이지 주소인 root : " + root);
        if(root.contains("member-view")) {
            return "redirect:/member/member-view"; //회원조회 페이지로 리다이렉트

        } else if (root.contains("reqList")) {
            return "redirect:/member/reqList"; //가입승인 페이지로 리다이렉트

        } else {
            return "redirect:/member/member-view"; //회원조회 페이지로 리다이렉트
        }
    }







    //2. 회원등록
    //2-1.직원목록 - 새로운 페이징 클래스 적용
    @GetMapping("/regist")
    public ModelAndView getEmployeeList(@RequestParam(defaultValue="1") int page, ModelAndView model) {

        int pageSize = 10; //페이지당 항목 수

        int totalCnt = memberService.totalEmployeeCount();
        System.out.println("db에서 조회한 총 직원목록 항목수 : " + totalCnt);
        PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);
        model.addObject("totalCnt", totalCnt);

        Map<String, Integer> map = new HashMap<>();
        map.put("startRow", pageHandler.getStartRow());
        map.put("endRow", pageHandler.getEndRow());

        List<EmployeeDTO> employeeList = memberService.getEmployeeList(map);
        model.addObject("employeeList", employeeList);
        model.addObject("pageHandler", pageHandler);
        model.setViewName("admin/member/member-regist1");
        return model;
    }



    //2-2. 등록버튼 눌렀을때 이동하는 등록페이지
    @PostMapping("/registPage") //@ResponseBody를 붙이는건 ajax일때만 해당
    public ModelAndView registPage(@ModelAttribute EmployeeDTO employee) {
                                    //form.submit()해서 보내면 @ModelAttribute이고 body에 담아서 보내면 @RequestBody임

        ModelAndView model = new ModelAndView("admin/member/member-regist2");
        System.out.println("employeeDTO로 들어왔는지 확인 : " + employee);
        model.addObject("employee", employee);

        return model;
    }

    //2-3. 등록기능
    @PostMapping("/registMember")
    public String registMember(@ModelAttribute MemberDTO member, RedirectAttributes rttr) throws RegistMemberException {

        System.out.println("회원등록 submit 후 들어오는지 확인---------------------");
        System.out.println("회원등록 member = " + member);

        //비밀번호 암호화
        member.setMemPwd(passwordEncoder.encode(member.getMemPwd()));

        memberService.registMember(member);

        rttr.addFlashAttribute("message", "회원등록 RedirectAttributes 확인");

        return "redirect:/member/regist"; //등록 후 회원등록(목록) 페이지로 리다이렉트
    }


    //3. 가입승인
    //3-1. 가입요청 목록(회원가입한 계약직 목록 - 승인대기 항목들)
//    @GetMapping("/reqList")
//    public ModelAndView getReqList(@RequestParam(value="currentPage", defaultValue="1") int currentPage, ModelAndView model) {
//
//        System.out.println("-------------가입요청리스트");
//
//        //신규 가입요청 수
//        int reqJoinCount = memberService.reqJoinCount();
//        System.out.println("가입요청 수 : " + reqJoinCount);
//        model.addObject("reqJoinCount", reqJoinCount);
//
//        //신규 가입요청 목록
//        List<MemberDTO> reqList = memberService.reqList();
//        model.addObject("reqList", reqList);
//
//        model.setViewName("admin/member/member-permission");
//        return model;
//    }


    //가입승인 - 새로운 페이징 클래스 적용
    @GetMapping("/reqList")
    public ModelAndView getReqList(@RequestParam(defaultValue="1") int page, ModelAndView model) {
        int pageSize = 10;


        //신규 가입요청 수
        int totalCnt = memberService.reqJoinCount();
        System.out.println("db에서 조회한 총 가입승인대상 항목수 : " + totalCnt);
        PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);
        model.addObject("totalCnt", totalCnt);

        Map<String, Integer> map = new HashMap<>();
        map.put("startRow", pageHandler.getStartRow());
        map.put("endRow", pageHandler.getEndRow());

        //신규 가입요청 목록
        List<MemberDTO> reqList = memberService.reqList(map);
        model.addObject("reqList", reqList);
        model.addObject("pageHandler", pageHandler);

        model.setViewName("admin/member/member-permission");
        return model;
    }




    //3-2. 승인버튼 클릭시
    @PostMapping("/permission")
    public String updateForPermission(@RequestParam ArrayList<String> empNos) throws RegistMemberException {
        System.out.println("체크박스 체크된 사번의 배열이 잘 들어오는지 확인 : " + empNos);
        memberService.updateForPermission(empNos);
        return "redirect:/member/reqList";
    }
    //3-3. 거절버튼 클릭시 계정삭제버튼을 누른것과 같이 deleteMember() 호출하며, 메서드내에서 리다이렉트 주소는 다르게 분기되도록함




    //4. 포인트지급 페이지
    //4-1. 포인트지급을 위한 회원목록 조회
    @GetMapping("/memberListForPoint")
    public ModelAndView getMemberListforPoint(@RequestParam(defaultValue="1") int page, ModelAndView model) {

        int pageSize = 10;
        int totalCnt = memberService.totalMemberCount(); //총 항목 수(검색 조건 적용)
        PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);
        model.addObject("totalCnt", totalCnt);

        Map<String, Integer> map = new HashMap<>();
        map.put("startRow", pageHandler.getStartRow());
        map.put("endRow", pageHandler.getEndRow());

        List<MemberDTO> memberList = memberService.getMemberListforPoint(map);
        System.out.println("복지포인트지급을 위한 회원목록 : " + memberList);
        model.addObject("memberList", memberList);
        model.addObject("pageHandler", pageHandler);

        //선택된 체크박스의 수
        int selectedElementsCnt = 0;
        System.out.println("선택된 체크박스의 수 : " + selectedElementsCnt);
       // model.addObject("selectedElementsCnt", selectedElementsCnt);

        model.setViewName("admin/member/member-givePoint");
        return model;
    }




    //4-2. 포인트 지급에 대한 ajax요청을 받기
    @ResponseBody
    @PostMapping("/givePoint")
    public Map<String, String> givePoint(@RequestBody PointHistoryDTO pointHistoryDTO) throws PointException {


        System.out.println("-----------------------------포인트지급ajax요청 받는지 확인");
        System.out.println("체크박스 체크된 사번의 배열이 잘 들어오는지 확인 : " + pointHistoryDTO.getEmpNos());
        System.out.println("선택회원수 : " + pointHistoryDTO.getSelectedCount());
        System.out.println("지급사유 : " + pointHistoryDTO.getSelectedReason());
        System.out.println("회원당지급액 : " + pointHistoryDTO.getSelectedAmount());

        //포인트이력테이블 인서트
        memberService.insertPointHistory(pointHistoryDTO);

        Map<String, String> map = new HashMap<>();
        map.put("locationroot", "/member/memberListForPoint"); //ajax 성공시 동작하는 리다이렉트주소로 사용
        return map;
    }


    //5. 포인트 지급이력 페이지

    //포인트지급이력(요약)
    @GetMapping("/pointHistorySummary")
    public ModelAndView pointHistorySummary(@RequestParam(defaultValue="1") int page, ModelAndView model) {

        int pageSize = 10;

        int totalCnt = memberService.pointHistorySummaryCount();
        System.out.println("db에서 조회한 총 가입승인대상 항목수 : " + totalCnt);
        PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);
        model.addObject("totalCnt", totalCnt);

        Map<String, Integer> map = new HashMap<>();
        map.put("startRow", pageHandler.getStartRow());
        map.put("endRow", pageHandler.getEndRow());


        List<PointHistoryDTO> pointHistorySummaryList = memberService.pointHistorySummary(map);
        System.out.println("컨트롤러에 지급이력요약목록 들어오는지 확인 : " + pointHistorySummaryList);

        model.addObject("pointHistorySummaryList", pointHistorySummaryList);
        model.addObject("pageHandler", pageHandler);
        model.setViewName("admin/member/member-givePointHistory1");
        return model;
    }


    //포인트지급이력(상세) - 페이징 처리 전
//    @GetMapping("/pointHistoryDetail")
//    public ModelAndView pointHistoryDetail(@RequestParam int eventId, ModelAndView model) {
//
//        System.out.println("컨트롤러에 eventId가 들어오는지 확인 : " + eventId);
//
//        List<PointHistoryDTO> pointHistoryDetailList = memberService.pointHistoryDetail(eventId);
//        System.out.println("컨트롤러에 지급이력상세목록 들어오는지 확인 : " + pointHistoryDetailList);
//
//        model.addObject("pointHistoryDetailList", pointHistoryDetailList);
//        model.setViewName("admin/member/member-givePointHistory2");
//        return model;
//    }


    //포인트지급이력(상세) - 페이징 처리 적용
    @GetMapping("/pointHistoryDetail")
    public ModelAndView pointHistoryDetail(@RequestParam int eventId
                                    , @RequestParam(defaultValue="1") int page, ModelAndView model) {

        System.out.println("컨트롤러에 eventId가 들어오는지 확인 : " + eventId);

        int pageSize = 10;
        int totalCnt = memberService.pointHistoryDetailCount(eventId);
        System.out.println("db에서 조회한 총 가입승인대상 항목수 : " + totalCnt);
        PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);
        model.addObject("totalCnt", totalCnt);

        Map<String, Integer> map = new HashMap<>();
        map.put("startRow", pageHandler.getStartRow());
        map.put("endRow", pageHandler.getEndRow());
        //eventId도 맵에 담는다
        map.put("eventId", eventId);

        List<PointHistoryDTO> pointHistoryDetailList = memberService.pointHistoryDetail(map);
        System.out.println("컨트롤러에 지급이력상세목록 들어오는지 확인 : " + pointHistoryDetailList);

        model.addObject("pointHistoryDetailList", pointHistoryDetailList);
        model.addObject("pageHandler", pageHandler);
        model.setViewName("admin/member/member-givePointHistory2");
        return model;
    }















//-------------------------------------------------------------------------------------------------------------




//    로그인 창

    /* 로그인창을 띄웁시다. */
    @GetMapping("login")
    public String login(Model model){

        return "member/login";

    }

    @PostMapping("login")
    public String loginForm(@RequestParam("empNo") String empNo,
                            @RequestParam("memPwd") String memPwd){

//        System.out.println("empNo = " + empNo + ", memPwd = " + memPwd);
        return null;

    }
    @PostMapping("test")
    public String testForm(Model model){

        return "member/test";
    }

    @GetMapping("error")
    public String error(Model model){

        return "member/error";
    }








}













