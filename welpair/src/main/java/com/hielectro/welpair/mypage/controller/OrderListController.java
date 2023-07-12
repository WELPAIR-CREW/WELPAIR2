package com.hielectro.welpair.mypage.controller;

import com.hielectro.welpair.mypage.model.dto.OrderListDTO;
import com.hielectro.welpair.mypage.model.service.OrderListService;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

import static com.hielectro.welpair.common.PriceCalculator.empNo;
import static java.util.Optional.*;


@Controller
@RequestMapping("/mypage/myorder")
public class OrderListController {

    private final OrderListService orderListService;

    public OrderListController(OrderListService orderListService) {
        this.orderListService = orderListService;
    }


    // 주문내역 리스트
    @GetMapping("/list")
    public String myorderList(Model model) {

        List<OrderListDTO> orderList = orderListService.selectOrderList(empNo);

        orderList.stream().filter(item -> Optional.<String>ofNullable(item.getDeliveryStatus()).isEmpty())
                .forEach(item -> item.setDeliveryStatus("배송준비중"));

        System.out.println("orderList = " + orderList);


        model.addAttribute("orderList", orderList);

        return "consumer/mypage/myorder";

        // 주문일자, 주문번호, 주문금액, // , 상품명 (NULL이면 판매중지 상품), 수량, // 배송상태, 주문상태(일반주문)
        // order, productorder, product //// delivery,(==NULL이면 배송준비중)

    }

    // 주문상세내역
    @GetMapping("/detail/{id}")
    public String myorderDetail() {

        // + 상세페이지 들어갈 오더넘버아이디 주소

        return "consumer/mypage/?" + "id";

    }



}
