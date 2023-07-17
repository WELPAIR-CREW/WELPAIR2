package com.hielectro.welpair.mypage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.mypage.model.dto.OrderDetailDTO;
import com.hielectro.welpair.mypage.model.service.OrderDetailService;
import com.hielectro.welpair.payment.model.dto.PaymentDTO;
@Controller
@RequestMapping("/mypage/myorder")
@PreAuthorize("hasRole('MEMBER')")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("detail/{orderNo}")
    public String orderDetail(@PathVariable(value="orderNo") String orderNo, Model model, @AuthenticationPrincipal User user){

        System.out.println("orderNo = " + orderNo);

        List<OrderDetailDTO> orderList = orderDetailService.selectOrderDetail(orderNo, user.getUsername());

        System.out.println("orderList = " + orderList);

        OrderDetailDTO orderDetail = new OrderDetailDTO();

        // 주문번호, 주문일시, 주문총금액 셋팅
        orderDetail.setOrderNo(orderList.get(0).getOrderNo());
        orderDetail.setOrderDate(orderList.get(0).getOrderDate());
        orderDetail.setTotalPrice(orderList.get(0).getTotalPrice());

        // 주문유형
        orderDetail.setOrderType(orderList.get(0).getOrderType());

        // 주문상품이름, 수량, 가격 셋팅
        Map<String, ProductDTO> prdmap = new HashMap<>();
        // 결제유형, 결제유형별 금액 셋팅
        Map<String, PaymentDTO> paymap = new HashMap<>();

        for(OrderDetailDTO order : orderList){

            ProductDTO product = new ProductDTO();
            if(Optional.ofNullable(order.getProductName()).isEmpty()){
                System.out.println("null1");
                product.setProductName("판매중지상품");
                product.setProductAmount(0);
                product.setProductPrice(0);
            } else{
                product.setProductName(order.getProductName());
                product.setProductAmount(order.getProductOrderAmount());
                product.setProductPrice(order.getPrice());

            }

            // product name으로 key를 주기 때문에 name이 중복이면 덮어써질 것
            prdmap.put(product.getProductName(), product);

            PaymentDTO payment = new PaymentDTO();

            if(Optional.ofNullable(order.getPaymentType()).isEmpty()){
                System.out.println("null2");

                payment.setPaymentType(" ");
                payment.setPaymentPrice(0);

            } else {
                payment.setPaymentType(order.getPaymentType());
                payment.setPaymentPrice(order.getPaymentPrice());
            }
                // payment Type으로 key를 주기 때문에 중복이면 덮어써질 것
                paymap.put(payment.getPaymentType(), payment);
        }


        System.out.println("상품 맵 출력=============>>>>>> " + prdmap.values());
        // 배송상태
        orderList.stream().filter(d ->  Optional.ofNullable(d.getDeliveryStatus())
                .isEmpty()).forEach(d -> d.setDeliveryStatus("배송준비중"));
        if(orderList.stream().allMatch(d -> d.getDeliveryStatus().contains("배송준비중"))){
            orderDetail.setDeliveryStatus("배송준비중");
        } else if (orderList.stream().allMatch(d -> d.getDeliveryStatus().contains("배송중"))){
            orderDetail.setDeliveryStatus("배송중");
        } else if(orderList.stream().allMatch(d -> d.getDeliveryStatus().contains("배송완료"))){
            orderDetail.setDeliveryStatus("배송완료");
        } else {
            orderDetail.setDeliveryStatus("배송준비중");
        }

        // 수령인, 수령인연락처, 수령인 주소, 배송지정일 셋팅
        orderDetail.setAddressName(orderList.get(0).getAddressName());
        orderDetail.setAddressPhone(orderList.get(0).getAddressPhone());
        orderDetail.setAddressDetail(orderList.get(0).getAddressDetail().replace("/", "  "));

        if(Optional.ofNullable(orderList.get(0).getDeliveryDate()).isEmpty()){

        } else {
            orderDetail.setDeliveryDate(orderList.get(0).getDeliveryDate());
        }


        // 확인
        System.out.println("orderDetail 그외 주문 총 정보 = " + orderDetail);
        System.out.println("paymap 결제 정보 = " + paymap);
        System.out.println("paymap 주문상품 정보 = " + prdmap);

        // 모델 내보내기
        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("paymap", paymap);
        model.addAttribute("prdmap", prdmap);

        return "consumer/mypage/myorder2";
    }
}
