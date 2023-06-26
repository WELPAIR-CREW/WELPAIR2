package com.hielectro.welpair.order.controller;

import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.order.model.dto.SellProductDTO;
import com.hielectro.welpair.order.model.service.OrderServiceImpl;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping({"/order"})
public class OrderController {
    private final OrderServiceImpl orderServiceImpl;
    private final MessageSource messageSource;

    public OrderController(OrderServiceImpl orderServiceImpl, MessageSource messageSource) {
        this.orderServiceImpl = orderServiceImpl;
        this.messageSource = messageSource;
    }

    @GetMapping({"{id}"})
    public String defaultLocation(@PathVariable("id") String url){
        return "consumer/order/" + url;
    }

    // 카트인서트용 메소드
    @ResponseBody
    @PostMapping(value = "/product-detail-test", produces = "application/json; charset=utf-8")
    public Map<String, String> addCart(HttpSession session, @ModelAttribute CartSellProductDTO cartSellProduct,
//                                       )
                          @RequestParam("empNo") String empNo) // session - 로그인된 사용자만 받기
    {

        // 카트별판매상품dto를 통해 매상품id와 수량 정보와, 회원정보ID가 넘어온다.
        System.out.println("선택상품 : " + cartSellProduct);

        // 그 전에 판매상품 ID를 통해 실제 존재하는 상품이며, 수량이 정상적인 수량인지 체크한다.
        SellProductDTO sellProduct =
                orderServiceImpl.checkoutSellProductId(cartSellProduct.getSellProductId());

        // 회원정보도 조회한다.
//        MemberDTO member = orderService.checkoutMemberById(empNo);
//        System.out.println(member);


        // 아이디 체크는 생략하자.(로그인이 됐다면 존재하는 회원이기에 통과)
        // 로그인파트에서 세션에 저장한 member 정보를 불러옴, 불러오지 못한다면 session 만료임
//        MemberDTO member = (MemberDTO)session.getAttribute("member");
//        System.out.println("회원아이디 : " + member.getEmpNo());

        Map<String, String> resultMap = new HashMap<>();

        if(cartSellProduct.getCartAmount() > 0 && sellProduct != null ) {

            // 회원번호를 통해 장바구니 테이블 pk를 생성한다.
//            int result1 = orderServiceImpl.addcart(member.getEmpNo());
            int result1 = orderServiceImpl.addcart(empNo);

            // 생성된 장바구니 PK를 불러온다.
            String cartNo = orderServiceImpl.selectCartNo();

            // 불러온 PK를 카트별판매상품 테이블에 담는다.(배송비 빼고 다 담김)
            cartSellProduct.setCartNo(cartNo);
            System.out.println("선택상품 : " +cartSellProduct);

            // 카트별판매상품 테이블에 데이터를 담는다.
            int result = orderServiceImpl.addCartSellProduct(cartSellProduct);

            if (result > 0) {
                //장바구니 담기 성공시
//                redirectAttributes.addFlashAttribute("successMessage", "선택상품이 장바구니에 담겼습니다.");
                System.out.println("장바구니 담기 성공1111");
//                model.addAttribute("successMessage", "장바구니 담기 성공");

                resultMap.put("successMessage", "장바구니 담기 성공");
                return resultMap;

            } else {   // 무언가 잘못된 입력111
//                redirectAttributes.addFlashAttribute("failMessage", "장바구니에 담는 것을 실패하였습니다222.");
                System.out.println("장바구니 담기 실패2222");
//                model.addAttribute("failMessage", "장바구니 담기 실패1");
                resultMap.put("failMessage", "장바구니 담기 실패");
                return resultMap;

            }
        } else {   // 무언가 잘못된 상품조회222
//            redirectAttributes.addFlashAttribute("failMessage", "장바구니에 담는 것을 실패하였습니다.111");
            System.out.println("장바구니 담기 실패1111");
//            model.addAttribute("failMessage", "장바구니 담기 실패22");
            resultMap.put("failMessage", "장바구니 담기 실패");
            return resultMap;
        }
        }

//        mv.setViewName("redirect:/order/product-detail-test");

//        return "redirect:consumer/order/product-detail-test";
    }




