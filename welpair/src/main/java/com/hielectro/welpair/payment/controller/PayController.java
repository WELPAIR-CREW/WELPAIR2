package com.hielectro.welpair.payment.controller;

import com.hielectro.welpair.common.PriceCalculator;
import com.hielectro.welpair.order.controller.CartController;
import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.payment.model.dto.CartPayReqDTO;
import com.hielectro.welpair.payment.model.service.PayService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping({"/payment"})
public class PayController {

    private final PayService payService;

    private final String empNo = "E00017";  // 나중에 지우기


    private PayController(PayService payService) {
        this.payService = payService;
    }

    @GetMapping("pay")
    public String gotopay(@ModelAttribute CartPayReqDTO cartPayList, Model model
                            , String empNo){

        System.out.println("======payment GetMapping 들어옴===========");
        System.out.println(cartPayList);
        System.out.println(cartPayList.getCartPayList().get(0));
        model.addAttribute("cartPayPrice", cartPayList.getCartPayList().get(0));


        // 1. 선택 상품 뿌리기
        List<CartGeneralDTO> payList = payService.selectProductById(cartPayList);
        System.out.println();
        System.out.println("============================payList 반환받음 : " + payList);
        System.out.println(payList.size());
        for(CartGeneralDTO pay : payList){
            pay.setPrice(new PriceCalculator().amountOfPrice(pay.getProduct().getProductPrice(),
                    pay.getSellProduct().getDiscount(), cartPayList.));

        }
        model.addAttribute("payList", payList);


        // 2. 합계금액 뿌리기

        // 3. 멤버 복지포인트 조회해오기




        // [{"cartSellProduct":{"sellProductId":"SP45","cartAmount":2},"exptPrice":null,"exptDeliveryPrice":null,"exptTotalPrice":null},
        // {"cartSellProduct":{"sellProductId":"SP50","cartAmount":3},"exptPrice":null,"exptDeliveryPrice":null,"exptTotalPrice":null}]

        return "consumer/payment/pay";
    }





}
