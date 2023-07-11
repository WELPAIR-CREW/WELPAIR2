package com.hielectro.welpair.mypage.controller;


import com.hielectro.welpair.member.controller.PointException;
import com.hielectro.welpair.member.model.dto.EmployeeDTO;
import com.hielectro.welpair.member.model.dto.PointHistoryDTO;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;
import com.hielectro.welpair.mypage.model.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
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

    //로그인 체크할것!


    private final MypageService mypageService;

    @Autowired
    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

//1.
    //회원정보 페이지로 이동시 본인(비밀번호) 인증 페이지
    @GetMapping("/checkPwd")
    public String checkPwd() {

        return "consumer/mypage/myinfo1";
    }

    //회원정보수정페이지
    @GetMapping("/editMyInfo")
    public String editMyInfo(AuthenticatedPrincipal auth) {

//        auth.getName() 널값이면(접속중이 아니면) 로그인 페이지로 돌려보낸다

        return "consumer/mypage/myinfo2";
    }


//2.
    //배송지목록
    @GetMapping("/myAddress")
    public ModelAndView myAddress(@RequestParam(required = false) String empNo, ModelAndView model) {

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
    public ModelAndView myAddress2(@RequestParam String empNo, ModelAndView model) {

        System.out.println("배송지 등록 페이지---------------");
        System.out.println("empNo 확인 : " + empNo);

        model.addObject("empNo", empNo);
        model.setViewName("consumer/mypage/myaddress2");
        return model;
    }


    //배송지 폼 등록하기 버튼(ajax요청을 받는 핸들러메소드)
    @ResponseBody
    @PostMapping("/registAddress")
    public Map<String, String> registAddress(@RequestBody AddressDTO addressDTO) throws Exception {

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



//3.
    //위시리스트 css수정필요
    @GetMapping("/wishlist")
    public String wishlist() {

        return "consumer/mypage/wishlist";
    }


//4.
    //포인트 css일부수정필요
    @GetMapping("/myPoint")
    public String myPoint() {

        return "consumer/mypage/mypoint";
    }


//5.
    //내가쓴글-문의목록
    //*****리뷰 페이지 html 재작성해야함
    @GetMapping("/myPost")
    public String myPost() {

        return "consumer/mypage/myqna";
    }







}
