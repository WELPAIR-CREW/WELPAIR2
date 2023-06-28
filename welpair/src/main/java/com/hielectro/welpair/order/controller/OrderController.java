package com.hielectro.welpair.order.controller;

import com.hielectro.welpair.order.model.dto.CartDTO;
import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.order.model.service.OrderService;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping({"/order"})
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // default 매핑 메소드
    @GetMapping({"{id}"})
    public String defaultLocation(@PathVariable("id") String url) {
        return "/consumer/order/" + url;
    }



    // 카트인서트용 메소드
    @ResponseBody
    @PostMapping(value = "/product-detail-test", produces = "application/json; charset=utf-8")
    public Map<String, String> addCart(@ModelAttribute CartSellProductDTO cartSellProduct,
                                       @RequestParam("empNo") String empNo)
    {
        // 카트별판매상품dto를 통해 매상품id와 수량 정보와, 회원정보ID가 넘어온다.
        System.out.println("선택상품 : " + cartSellProduct);

        // 결과 메세지 전달 map 객체
        Map<String, String> resultMap = new HashMap<>();

        // 상품 조회
        List<SellProductDTO> sellProductList =
                orderService.findSellProductByCode(cartSellProduct.getSellProductId());


        // 1. 정상 수량인지 체크(프론트에서도 검증)
        if(cartSellProduct.getCartAmount() < 1 ){
            resultMap.put("message", "수량이 잘못되었습니다.");
            return resultMap;
        }
        // 2. 판매상품 ID를 통해 현재 판매중인 상품인지 조회
        else if(sellProductList.size() < 1 ){
            resultMap.put("message", "판매중인 상품이 아닙니다.");
            return resultMap;
        }
        // 3. 정상 데이터로 검증 통과
        else {

            // 회원정보를 조회하여 카트가 생성되어있으면 카트번호를 조회해온다. 없는 경우 생성한다.
            CartDTO cart = orderService.checkoutCartByMemberId(empNo);
            log.info("cart");

            // 장바구니 미생성 회원
            if(cart == null) {
                // 장바구니 테이블을 생성한다.
                orderService.makeCart(empNo);
                // 다시 장바구니 정보 조회
                cart = orderService.checkoutCartByMemberId(empNo);
                // 장바구니(카트) 넘버를 세팅한다.
                cartSellProduct.setCartNo(cart.getCartNo());
            }

            cartSellProduct.setCartNo(cart.getCartNo());
            System.out.println("cartSell 정보 : " + cartSellProduct);

            // 장바구니에 같은 상품을 담은 경우
            int checkPrd = orderService.checkoutCartProductById(cartSellProduct);
            if(checkPrd > 0 ){
                resultMap.put("message", "이미 장바구니에 존재하는 상품입니다.");
                return resultMap;
            }

            // cartSellProduct 테이블 데이터 삽입하러 가기
            int result = orderService.addCartSellProduct(cartSellProduct);
            if (result > 0) {
                //장바구니 담기 성공시
                System.out.println("장바구니 담기 성공1111");
                resultMap.put("message", "장바구니 담기에 성공하였습니다.");
                return resultMap;

            } else {   // 실패
                log.info("log 확인");
                System.out.println("장바구니 담기 실패2222");
                resultMap.put("message", "장바구니 담기에 실패하였습니다. 다시 시도해주세요.");
                return resultMap;
            }
        }
    }
}




