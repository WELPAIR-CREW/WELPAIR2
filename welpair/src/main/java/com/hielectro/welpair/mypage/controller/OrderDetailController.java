package com.hielectro.welpair.mypage.controller;

import com.hielectro.welpair.mypage.model.dto.OrderDetailDTO;
import com.hielectro.welpair.mypage.model.service.OrderDetailService;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hielectro.welpair.common.PriceCalculator.empNo;

@Controller
@RequestMapping("/mypage/myorder")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("detail/{orderNo}")
    public String orderDetail(@PathVariable(value="orderNo") String orderNo, Model model){

        // 주문번호, 주문일자, 총금액, 주문상태, 배송상태, 배송지 정보  -1
        // 상품명, 수량 , 결제수단, - list

        System.out.println("들어왓니?????????????");
        System.out.println("orderNo = " + orderNo);

        List<OrderDTO> orderList = orderDetailService.selectOrderDetail(orderNo, empNo);

        System.out.println("orderDetailList = " + orderList);

        OrderDetailDTO orderDetail = new OrderDetailDTO();

        for (OrderDTO order : orderList) {

            orderDetail.setOrderNo(order.getOrderNo());
            orderDetail.setOrderDate(order.getOrderDate());
            orderDetail.setTotalPrice(order.getTotalPrice());
            orderDetail.setKakaoPay(order.getOrderPayment()
                    .getPaymentList().stream()
                    .filter(i -> i.getPaymentType()
                            .contains("카카오페이"))
                    .mapToInt(i -> i.getPaymentPrice())
                    .sum());
            orderDetail.setPoint(order.getOrderPayment()
                    .getPaymentList().stream()
                    .filter(i -> i.getPaymentType()
                            .contains("복지"))
                    .mapToInt(i -> i.getPaymentPrice())
                    .sum());
            orderDetail.setOrderType(order.getOrderType());


            if(order.getProductOrderList().stream().allMatch(item -> item.getDelivery()
                            .getDeliveryStatus().contains("배송준비중"))){
                orderDetail.setDeliveryStatus("배송준비중");
            } else if(order.getProductOrderList().stream().allMatch(item -> item.getDelivery()
                    .getDeliveryStatus().contains("배송중"))){
                orderDetail.setDeliveryStatus("배송중");
            } else if(order.getProductOrderList().stream().allMatch(item -> item.getDelivery()
                    .getDeliveryStatus().contains("배송완료"))){
                orderDetail.setDeliveryStatus("배송완료");
            } else {
                orderDetail.setDeliveryStatus("배송준비중");
            }

            orderDetail.setAddressDetail(order.getMember().getAddress().get(0).getAddressDetail().replace("/", " "));
            orderDetail.setAddressName(order.getMember().getAddress().get(0).getAddressName());
            orderDetail.setAddressPhone(order.getMember().getAddress().get(0).getAddressPhone());

            order.getProductOrderList().forEach(item -> orderDetail.getProductList().add(item));
        }

        System.out.println("orderDetail = " + orderDetail);

        model.addAttribute("orderDetail", orderDetail);

        return "/consumer/mypage/myorder2";
    }
}
