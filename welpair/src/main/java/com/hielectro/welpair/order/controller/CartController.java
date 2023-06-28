package com.hielectro.welpair.order.controller;


import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.service.CartService;
import org.apache.catalina.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping({"/"})
public class CartController {

    private final CartService cartService;

    private CartController( CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping("cart")
    public String cartList(Model model
                         ,@AuthenticationPrincipal User user
                           , @RequestParam("empNo") String empNo
                           ){


//        System.out.println(user);
//        System.out.println(user.getUsername());

        System.out.println(empNo);

        // test 결과값 1
//        int result =  cartService.testCartAllInfoSelect(empNo);
//        System.out.println("test 결과값 : " + result);

        // 해당 사번으로 조인 테이블 조회하기
        List<CartGeneralDTO> cartList = cartService.cartAllInfoSelect(empNo);
        System.out.println(cartList);  // null ?????


//        cart.setCart();
//        cartList.add();

        // 예상결제금액 : 단품별 합계 금액의 총 합계
        int exptPrice = 0;
        int exptDeliveryPrice = 0;

        for(CartGeneralDTO cart : cartList){
            exptPrice += cart.getTotalPrice();
            exptDeliveryPrice += cart.getCartSellProduct().getDeliveryPrice();
        }

        int exptTotalPrice = exptPrice + exptDeliveryPrice;



        return "consumer/order/cart";

    }

//    @GetMapping({"{id}"})
//    public String defaultLocation(@PathVariable("id") String url){
//        return "/consumer/order/" + url;
//    }






}
