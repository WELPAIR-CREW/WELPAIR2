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

    //로그인 체크할것!


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

        //로그인 체크 방법 : auth.getName() 널값이면(접속중이 아니면) 로그인 페이지로 돌려보낸다

        if (user != null) { //로그인 돼있는 경우 본인확인창 페이지로 이동시킨다
//            return checkPwd();
            return "redirect:/mypage/checkPwd";

        } else {                        //로그인 안돼있다면 로그인페이지로 보낸다
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
//        String userPwd = user.getPassword(); //null
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
            map.put("locationroot", "/mypage/editMyInfoPage");  //수정 페이지
            return map;

        } else {
            Map<String, String> map = new HashMap<>();
            map.put("locationroot", "/mypage/checkPwd");
            return map;
        }
    }

    @GetMapping("/editMyInfoPage")
    public String editMyInfoPage() {

        //리턴하면서 아이디 등 기본값을 input태그에 뿌려줄 수 있게 데이터 전달
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
    public ModelAndView myAddress(@RequestParam(required = false) String empNo, ModelAndView model) {

        empNo = "E00062";

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


    //기존의 기본배송지 초기화
    @PostMapping("/resetDefaultAddress")
    public void resetDefaultAddress(@RequestParam String empNo) throws Exception {

        System.out.println("---------------------기본배송지 초기화 메소드 실행");
        empNo = "E00062";

        mypageService.resetDefaultAddress(empNo);
    }







    //3.
    //위시리스트 css수정필요
//    @GetMapping("/wishlist")
//    public String wishlist() {
//
//        return "consumer/mypage/wishlist";
//    }
    @GetMapping("/wishlist")
    public ModelAndView getWishlistList(ModelAndView model) {

        String empNo = "E00026";

        String wishId = mypageService.getWishId(empNo);
        System.out.println("회원 " + empNo + "의 wishId 값 조회 : " + wishId);

        List<WishlistSellProductDTO> wishItemList = mypageService.getWishlistList(wishId);
        System.out.println("리스트 출력 : " + wishItemList);

        model.addObject("wishItemList", wishItemList);
        model.setViewName("consumer/mypage/wishlist");
        return model;
    }


    //4. 마이포인트
    @GetMapping("/myPoint")
    public ModelAndView myPoint(@RequestParam(defaultValue = "1") int page, ModelAndView model
            , @RequestParam(value = "type", required = false) String pointType) {

        //@@@로그인하여 현재 접속중인 empNo값을 인자로 넘겨야할것
        //일단 인위적으로 설정
        String empNo = "E00026";


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
//    @GetMapping("/myPost")
//    public String myPost() {
//
//        return "consumer/mypage/myqna";
//    }
    @GetMapping("/myPost")
    public ModelAndView myPost(@RequestParam(defaultValue = "1") int page, ModelAndView model) {

        String empNo = "T00015";

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
    public String myorder() {

        return "consumer/mypage/myorder";
    }



    //7.마이페이지 메인화면 (우측 상단 MY 클릭시 진입)
    @GetMapping({"/mypageMain", "/"})
    public String mypageMain() {

        return "consumer/mypage/mypage-main";
    }


}
