package com.hielectro.welpair.order.controller;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.order.model.dto.CartDTO;
import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.order.model.service.OrderService;
import com.hielectro.welpair.order.model.service.OrderServiceImpl;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.ibatis.mapping.ResultMap;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Slf4j
@Controller
@RequestMapping({"/order"})
public class OrderController {
    private final OrderService orderService;
    private final MessageSource messageSource;

    public OrderController(OrderService orderService, MessageSource messageSource) {
        this.orderService = orderService;
        this.messageSource = messageSource;
    }

    // default 매핑 메소드
    @GetMapping({"{id}"})
    public String defaultLocation(@PathVariable("id") String url) {
        return "/consumer/order/" + url;
    }



    // 카트인서트용 메소드
    @ResponseBody
    @PostMapping(value = "/product-detail-test", produces = "application/json; charset=utf-8")
    public Map<String, String> addCart(HttpSession session, @ModelAttribute CartSellProductDTO cartSellProduct,
                                        ModelAndView mv, RedirectAttributes rttr,
//                                       )
                                       @RequestParam("empNo") String empNo) // session - 로그인된 사용자만 받기
    {
        // 카트별판매상품dto를 통해 매상품id와 수량 정보와, 회원정보ID가 넘어온다.
        System.out.println("선택상품 : " + cartSellProduct);

        // 결과 메세지 전달 map 객체
        Map<String, String> resultMap = new HashMap<>();

        // 세션만료 체크. 아이디 체크는 생략하자.(로그인이 됐다면 존재하는 회원이기에 통과)
        // 로그인파트에서 세션에 저장한 member 정보를 불러옴, 불러오지 못한다면 session 만료임
        MemberDTO member = (MemberDTO)session.getAttribute("member");
        System.out.println("회원아이디 : " + member.getEmpNo());

        // 상품 조회
        List<SellProductDTO> sellProductList =
                orderService.findSellProductByCode(cartSellProduct.getSellProductId());

        // 1. 로그인검증
        if(member == null ){
            // 로그인 페이지로 이동
            mv.setViewName("redirect:/consumer/member");
            resultMap.put("failMessage", mv.getViewName());
            return resultMap;
        }
        // 2. 정상 수량인지 체크
        else if(cartSellProduct.getCartAmount() < 1 ){
            resultMap.put("failMessage", "수량이 잘못되었습니다.");
            return resultMap;
        }
        // 3. 판매상품 ID를 통해 실제 존재하는 상품인지 조회
        else if(sellProductList.size() < 0 ){
            resultMap.put("failMessage", "판매중인 상품이 아닙니다.");
            return resultMap;
        }
        else {

            // 회원정보를 조회하여 카트가 생성되어있으면 카트번호를 조회해온다. 없는 경우 생성한다.
            CartDTO cart = orderService.checkoutCartByMemberId(empNo);
            System.out.println(cart);

            // 장바구니 미생성 회원
            if(cart == null) {
            // 장바구니 테이블을 생성한다.
            int result = orderService.makeCart(empNo);
            // 다시 장바구니 정보 조회
            cart = orderService.checkoutCartByMemberId(empNo);
            // 장바구니(카트) 넘버를 세팅한다.
            cartSellProduct.setCartNo(cart.getCartNo());
            }

            // cartSellProduct 테이블 데이터 삽입하러 가기
            int result = orderService.addCartSellProduct(cartSellProduct);

            if (result > 0) {
                //장바구니 담기 성공시
                System.out.println("장바구니 담기 성공1111");
                resultMap.put("successMessage", "장바구니 담기에 성공하였습니다.");
                return resultMap;

            } else {   // 실패
                log.info("log 확인");
                System.out.println("장바구니 담기 실패2222");
                resultMap.put("failMessage", "장바구니 담기에 실패하였습니다. 다시 시도해주세요.");
                return resultMap;
            }
        }
    }
}




