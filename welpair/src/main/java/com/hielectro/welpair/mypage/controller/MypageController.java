package com.hielectro.welpair.mypage.controller;


import com.hielectro.welpair.board.model.dto.BoardDTO;
import com.hielectro.welpair.member.controller.PageHandler;
import com.hielectro.welpair.member.controller.PointException;
import com.hielectro.welpair.member.model.dto.EmployeeDTO;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.dto.PointHistoryDTO;
import com.hielectro.welpair.member.model.dto.UserImpl;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;
import com.hielectro.welpair.mypage.model.dto.WishlistSellProductDTO;
import com.hielectro.welpair.mypage.model.service.MypageService;
import com.hielectro.welpair.regist.model.dao.RegistDAO;
import com.hielectro.welpair.regist.model.dto.RegistDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MypageController {


    //@@@마이페이지 메인, 마이페이지의 세부 페이지들 전부 로그인 체크하여 null일때에는 로그인페이지로 이동하도록 하기
    //회원정보 수정페이지 해결하기
    //위시리스트 페이지 css 보완
    //T00015(로그인)의 포인트 사용이력 데이터넣어주기
    //문의목록 페이지 css, 페이징, 누르면 해당 문의글로 이동하게, 문의하기, 삭제 기능


    private final MypageService mypageService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MypageController(MypageService mypageService, PasswordEncoder passwordEncoder) {
        this.mypageService = mypageService;
        this.passwordEncoder = passwordEncoder;
    }


    //DB에 저장된 비밀번호와 입력한 비밀번호가 일치하는지 확인하는 메소드
    public boolean confirmUser(String password, String dbPassword) {
        return passwordEncoder.matches(password, dbPassword);
    }



    //1. 헤더에서 회원정보(수정) 메뉴 클릭시
    @GetMapping("/editMyInfo")
    public String editMyInfo(@AuthenticationPrincipal User user) {

        if (user != null) { //로그인 돼있는 경우 본인확인창 페이지로 이동시킨다
            return "redirect:/mypage/checkPwd";

        } else {  //로그인 안돼있다면 로그인페이지로 보낸다
            return "/member/login";
        }
    }


    //본인인증 페이지(비밀번호 입력창)로드
    @GetMapping("/checkPwd")
    public String checkPwd() {
        return "consumer/mypage/myinfo1";
    }


    //비밀번호 입력 후 확인 버튼 클릭시의 ajax요청을 받는 핸들러메소드
    @ResponseBody
    @PostMapping("/checkPwd2")
    public Map<String, String> checkPwd2(@AuthenticationPrincipal UserImpl userImpl, @RequestParam String inputPassword) {

        //현재 로그인한 유저 정보
        String empNo = userImpl.getEmpNo();
        String memPwd = userImpl.getMemPwd();

        System.out.println("로그인한 empNo : " + empNo);
        System.out.println("DB의 비밀번호 memPwd 확인 : " + memPwd);

        System.out.println("입력한 비번 :  " + inputPassword); //ajax요청으로 전송된 데이터


        //입력한 비밀번호가 현재 로그인한 유저의 비밀번호와 같은지 비교
        boolean isSame = confirmUser(inputPassword, memPwd); //패스워드인코더의 메소드 이용하는 메소드

        System.out.println("isSame값 확인 : " + isSame);


        if(isSame) {
            //ajax 성공시 동작하는 get요청의 리다이렉트주소
            Map<String, String> map = new HashMap<>();
//            map.put("locationroot", "/mypage/editMyInfoPage");  //수정 페이지
            map.put("locationroot", "forward:/mypage/editMyInfoPage");
            return map;

        } else {
            Map<String, String> map = new HashMap<>();
            map.put("locationroot", "/mypage/checkPwd");
            return map;
        }
    }


    @GetMapping("/editMyInfoPage")
    public String editMyInfoPage(@AuthenticationPrincipal UserImpl user) {

        String empNo = user.getEmpNo();
        String memPwd = user.getMemPwd();
        String userName = user.getUsername();
        String userEmail = user.getEmployee().getEmpEmail();
        String userPhone = user.getEmployee().getEmpPhone();
        System.out.println("user정보 출력 : " + user.toString());

        return "consumer/mypage/myinfo2";
    }

    //회원정보수정 버튼 클릭시의 post요청
    @PostMapping("updateUserInfo")
    public String updateUserInfo(@ModelAttribute RegistDTO registDTO, @AuthenticationPrincipal User user) {
        System.out.println("-----------------회원정보update 메소드");
        System.out.println("@ModelAttribute DTO인 RegistDTO 필드값 : " + registDTO);
        System.out.println("@AuthenticationPrincipal 유저 : " + user);


        //비밀번호는 암호화해서 업데이트
        registDTO.setMemPwd(passwordEncoder.encode(registDTO.getMemPwd()));
//        mypageService.updateUserInfo(registDTO);

        return "redirect:/mypage/editMyInfoPage";
    }


    //2.
    //배송지목록
    @GetMapping("/myAddress")
    public ModelAndView myAddress(@AuthenticationPrincipal UserImpl user, @RequestParam(required = false) String empNo, ModelAndView model) {

        if(user == null) { //로그인 안된 경우
            model.setViewName("member/login"); //로그인 페이지로 보낸다
            return model;
        }

        //로그인한 사용자의 empNo를 불러온다
        empNo = user.getEmpNo();
        System.out.println("로그인한 사용자 : " + user);
        System.out.println("로그인한 사용자의 empNo : " + user.getEmpNo());

        Map<String, String> map = new HashMap<>();
        map.put("empNo", empNo);

        List<AddressDTO> addressList = mypageService.getAddressList(map);
        model.addObject("addressList", addressList);
        model.setViewName("consumer/mypage/myaddress1");
        return model;
    }


    //배송지 삭제버튼
    @ResponseBody
    @PostMapping("/deleteAddress")
    public Map<String, String> deleteAddress(@RequestParam String addressId) throws Exception {

        System.out.println("-----------------------------배송지 삭제 ajax요청 받는지 확인");
        System.out.println("배송지 아이디 확인 : " + addressId);

        //배송지 테이블 딜리트
        mypageService.deleteAddress(addressId);

        Map<String, String> map = new HashMap<>();
        map.put("locationroot", "/mypage/myAddress"); //ajax 성공시 동작하는 리다이렉트주소로 사용
        return map;
    }


    //배송지등록페이지
    @PostMapping("/myAddress2")
    public ModelAndView myAddress2(@AuthenticationPrincipal UserImpl user, ModelAndView model) {

        String empNo = user.getEmpNo();

        System.out.println("배송지 등록 페이지---------------");
        System.out.println("empNo 확인 : " + empNo);

        model.addObject("empNo", empNo);
        model.setViewName("consumer/mypage/myaddress2");
        return model;
    }


    //배송지 폼 등록하기 버튼(ajax요청을 받는 핸들러메소드)
    @ResponseBody
    @PostMapping("/registAddress")
    public Map<String, String> registAddress(@AuthenticationPrincipal UserImpl user, @RequestBody AddressDTO addressDTO) throws Exception {

        System.out.println("-----------------------------배송지 등록 ajax요청 받는지 확인");
        System.out.println("컨트롤러로 들어온 addressDTO 확인 : " + addressDTO);

        //배송지 아이디
        String nextAddressId = mypageService.nextAddressId();

        //배송지 주소 아이디 쿼리로 조회한뒤 없는 값으로 추가돼야함(시퀀스 이용)
        addressDTO.setAddressId(nextAddressId);

        System.out.println("다시 addressDTO 확인" + addressDTO);

        //배송지 테이블에 인서트
        mypageService.registAddress(addressDTO);

        Map<String, String> map = new HashMap<>();
        map.put("locationroot", "/mypage/myAddress"); //ajax 성공시 동작하는 리다이렉트주소로 사용
        return map;
    }


    //기존의 기본배송지 초기화
    @PostMapping("/resetDefaultAddress")
//    public void resetDefaultAddress(@RequestParam String empNo) throws Exception {
    public void resetDefaultAddress(@AuthenticationPrincipal UserImpl user) throws Exception {

        System.out.println("---------------------기본배송지 초기화 메소드 실행");
        String empNo = user.getEmpNo();
        System.out.println("로그인한 empNo : " + empNo);

        mypageService.resetDefaultAddress(empNo);
    }







    //3.
    //위시리스트 css수정필요
    @GetMapping("/wishlist")
    public ModelAndView getWishlistList(@AuthenticationPrincipal UserImpl user, ModelAndView model) {

        if(user == null) { //로그인 안된 경우
            model.setViewName("member/login"); //로그인 페이지로 보낸다
            return model;
        }

        String empNo = user.getEmpNo();
        System.out.println("위시리스트목록-----------로그인한 empNo : " + empNo);

        String wishId = mypageService.getWishId(empNo);
        System.out.println("회원 " + empNo + "의 wishId 값 조회 : " + wishId);


        //@@@wishId가 null인 경우에 예외처리하기
        //null인 경우 위시리스트 추가해주는 메소드 작성하기
        /*
        INSERT INTO T_WISH
        VALUES
        ('W'||LPAD(SEQ_WISH_ID.NEXTVAL,10,0)
        ,'T00015'
        ,SYSDATE );
        * */

        List<WishlistSellProductDTO> wishItemList = mypageService.getWishlistList(wishId);
        System.out.println("리스트 출력 : " + wishItemList);

        model.addObject("wishItemList", wishItemList);
        model.setViewName("consumer/mypage/wishlist");
        return model;
    }


    //4. 마이포인트
    @GetMapping("/myPoint")
    public ModelAndView myPoint(@RequestParam(defaultValue = "1") int page, ModelAndView model
            , @RequestParam(value = "type", required = false) String pointType
            , @AuthenticationPrincipal UserImpl user) {



        if(user == null) { //로그인 안된 경우
            model.setViewName("member/login"); //로그인 페이지로 보낸다
            return model;
        }


        String empNo = user.getEmpNo();
        System.out.println("----------포인트조회-------- 로그인한 사용자의 empNo : " + empNo);


        //페이징-------------------------------------------------------------
        int pageSize = 10; //페이지당 항목 수
        int totalCnt = mypageService.myPointListCount(empNo);
        System.out.println("db에서 조회한 총 마이포인트 목록 항목수 : " + totalCnt);
        PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);
        model.addObject("totalCnt", totalCnt);

        Map<String, Object> map = new HashMap<>();
        map.put("startRow", pageHandler.getStartRow());
        map.put("endRow", pageHandler.getEndRow());
        model.addObject("pageHandler", pageHandler);
        //------------------------------------------------------------------

        map.put("empNo", empNo);

        if (pointType != null && (pointType.equals("earn") || pointType.equals("used"))) {
            map.put("pointType", pointType);
        }

        System.out.println("pointType값 확인 : " + pointType);


        List<PointHistoryDTO> mypointList = mypageService.mypointList(map);
        model.addObject("mypointList", mypointList);

        //포인트 잔액 조회
        int pointBalance = mypageService.getPointBalance(empNo);
        model.addObject("pointBalance", pointBalance);

        model.setViewName("consumer/mypage/mypoint");
        return model;
    }


    //5.
    //내가쓴글-문의목록
    //*****리뷰 페이지 html 재작성해야함
    @GetMapping("/myPost")
    public ModelAndView myPost(@AuthenticationPrincipal UserImpl user, @RequestParam(defaultValue = "1") int page, ModelAndView model) {


        if(user == null) { //로그인 안된 경우
            model.setViewName("member/login"); //로그인 페이지로 보낸다
            return model;
        }


        String empNo = user.getEmpNo();
        System.out.println("-----------문의목록----------로그인한 empNo : " + empNo);

        //페이징-------------------------------------------------------------
        int pageSize = 10; //페이지당 항목 수
        int totalCnt = mypageService.myQnaCount(empNo);
        System.out.println("db에서 조회한 총 마이포인트 목록 항목수 : " + totalCnt);
        PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);
        model.addObject("totalCnt", totalCnt);

        Map<String, Object> map = new HashMap<>();
        map.put("startRow", pageHandler.getStartRow());
        map.put("endRow", pageHandler.getEndRow());
        model.addObject("pageHandler", pageHandler);
        //------------------------------------------------------------------

        map.put("empNo", empNo);
        List<BoardDTO> myQnaList = mypageService.myQnaList(empNo);
        System.out.println("myQnaList 출력 : " + myQnaList);
        model.addObject("myQnaList", myQnaList);
        model.setViewName("consumer/mypage/myqna");

        return model;
    }




    //6. 주문내역
    @GetMapping("/myorder")
    public String myorder(@AuthenticationPrincipal UserImpl user) {

        if(user == null) { //로그인 안된 경우
            return "/member/login";
        }

        return "consumer/mypage/myorder";
    }



    //7.마이페이지 메인화면 (우측 상단 MY 클릭시 진입)
    @GetMapping({"/mypageMain", "/"})
    public String mypageMain(@AuthenticationPrincipal UserImpl user) {

        System.out.println("로그인여부 user 조회 : " + user);

        if(user == null) { //로그인 안된 경우
            return "/member/login";
        }

        return "consumer/mypage/mypage-main";
    }


}
