package com.hielectro.welpair.payment.controller;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;
import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.payment.model.dto.OrderPayReqDTO;
import com.hielectro.welpair.payment.model.service.PayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String gotopay(@ModelAttribute OrderPayReqDTO orderPrdList, Model model){

        System.out.println("======payment GetMapping 들어옴===========");
        System.out.println(orderPrdList);
//        System.out.println(cartPayList.getCartPayList().get(0));
//        model.addAttribute("cartPayPrice", cartPayList.getCartPayList().get(0));



        // 1. 선택 상품 조회(생략해도 되지 않을까??????)
//        List<CartGeneralDTO> prdList = new ArrayList<>();

        int totalPrice = 0;
        for (int i = 0; i < orderPrdList.getOrderPrdList().size(); i++) {
            totalPrice += orderPrdList.getOrderPrdList().get(i).getProductOrderPrice();
        }
        // 합계금액
        orderPrdList.setTotalPaymentPrice(totalPrice);

        // 2. 상품 정보 다시 뿌리기
        model.addAttribute("orderPrdList", orderPrdList);


        // 3. 멤버 조회해오기

        // 3-1. (아이디 -> 배송지 전체테이블, 멤버 포인트 )
        System.out.println("empNo========`=========" + empNo);
        List<MemberDTO> memberAddressList = payService.selectMemberById(empNo);
        System.out.println(memberAddressList);
        model.addAttribute("memberAddressList", memberAddressList);



        return "consumer/payment/pay";
    }





}
