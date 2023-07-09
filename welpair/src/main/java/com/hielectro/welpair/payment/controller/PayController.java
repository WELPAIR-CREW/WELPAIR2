package com.hielectro.welpair.payment.controller;

import com.hielectro.welpair.common.PriceCalculator;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.payment.model.dto.OrderPayReqDTO;
import com.hielectro.welpair.payment.model.service.PayService;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLTransactionRollbackException;
import java.util.List;

import static com.hielectro.welpair.common.PriceCalculator.empNo;


@Slf4j
@Controller
@RequestMapping({"/payment"})
public class PayController {

    private final PayService payService;

    private PayController(PayService payService) {
        this.payService = payService;
    }

    @PostMapping("/payment.do")
    public String gotopay(@ModelAttribute("orderPrdList") OrderPayReqDTO orderPrdList, Model model
                          //            , @AuthenticationPrincipal User user
    ) throws Exception {

        System.out.println("========post mapping 들어옴===========");
        System.out.println(orderPrdList);


        // 1. 선택 상품 조회
        int totalPrice = 0;
        // 상품 조회해서 product에 데이터에서 받아온 id, 요청 수량에 따른 orderPrice 수정 못하게 셋팅하기
        for (ProductOrderDTO product : orderPrdList.getOrderPrdList()) {

        SellProductDTO prd = payService.selectProductById(product.getSellProductId());

            if (prd.getIsSell().equals('N')) {
                System.out.println("판매중인 상품이 아닙니다. 다시 주문해주세요.");
                System.out.println("해당 상품 : " + product.getSellproduct());
                model.addAttribute("suspendProduct", product.getSellproduct());
                return "/consumer/order/cart";
            }

            totalPrice += product.getProductOrderPrice();
            orderPrdList.setTotalPaymentPrice(totalPrice);
        }
        System.out.println("================================\n" + orderPrdList + "\n========================================");

        // 2. 상품 정보 다시 뿌리기
        model.addAttribute("orderPrdList", orderPrdList);


        // 3. 멤버 조회해오기
        // 3-1. (아이디 -> 배송지 전체테이블, 멤버 포인트 )
        System.out.println("empNo========`=========" + empNo);
        List<MemberDTO> memberAddressList = payService.selectMemberById(empNo);
        System.out.println(memberAddressList);
        model.addAttribute("memberAddressList", memberAddressList);

        return "/consumer/payment/payment";

    }

    // 결제수단에 따른 매핑 나누기
    @PostMapping("/payment.go")
    public String gotopay(@RequestBody OrderDTO order, RedirectAttributes rttr, Model model
                    //            , @AuthenticationPrincipal User user
                        ) throws Exception {

        log.info("리다이렉트용 매핑 컨트롤러 들어옴");

        // ORDER테이블에 데이터 넣어서 주문번호 orderNo 바로 받아오기
        order.setMemberNo(empNo);  // 회원 아이디, 나중에 로그인 열기
        boolean result = payService.insertOrder(order);

        if(!result) {
            log.info("주문테이블 insert 실패");
            return "/consumer/payment/pay-fail";
        }

        log.info(order.getOrderNo());
        rttr.addFlashAttribute("order", order);
//        model.addAttribute("order", order);

        // 카카오페이 결제 포함시
       if(order.getOrderPayment().getPaymentList().stream()
               .anyMatch(item -> item.getPaymentType().contains("카카오페이"))){
           return "redirect:/payment/kakaopay/do";

       } else {   // 복지포인트로 전액 결제 완료
           // success 매핑으로 가서 데이터 저장

           return "redirect:/payment/pay-success";
       }
    }

    @GetMapping("/pay-fail")
    public String payFail(@ModelAttribute("orderNo") String orderNo) throws Exception{

        log.info("pay-fail 매핑들어옴===> " + orderNo);
        // order 테이블 삭제해야함
        boolean result = payService.deleteOrder(orderNo);

        return "/consumer/payment/pay-fail";
    }

    @GetMapping("/pay-success")
    public String paySuccess(@ModelAttribute("order") OrderDTO order) throws Exception {

        log.info("pay-success 매핑들어옴===> " + order);
        // 1. payment insert // paymentNo 가져옴 ---> 2. orderPayment insert 동시진행

        // orderNo 미리 셋팅
        order.getOrderPayment().setOrderNo(order.getOrderNo());

        // tid가 null이면 테이블 데이터삽입시 오류가 생겨서 강제로 none 문자열값을 넣어준다.
        order.getOrderPayment().getPaymentList().stream()
                .filter(item -> item.getPaymentType().contains("복지"))
                .forEach(item -> item.setTid("NONE"));

        order.getOrderPayment().getPaymentList().forEach(item -> {
                    log.info("item : " + item);
                    try {

                        payService.insertPayment(item);
                        log.info("paymentNo : " + item.getPaymentNo() + " orderNo : " + order.getOrderNo());

                        payService.insertOrderPayment(order.getOrderNo(), item.getPaymentNo());

                    } catch (SQLTransactionRollbackException e) {
                        System.out.println("!!!!!!!!!!pay insert 실패!!!!!!!!!");
                        payService.deleteOrder(order.getOrderNo());
                    }
                }
        );

        // 3. productorder list insert
        order.getProductOrderList().forEach( product -> {
            log.info("product : " + product);
            product.setOrderNo(order.getOrderNo());
            try {
                payService.insertProductOrder(product);
            } catch (SQLTransactionRollbackException e) {
                System.out.println("!!!!!!!!!!productorder insert 실패!!!!!!!!!");
                payService.deleteOrder(order.getOrderNo());


            }
        });

        // 4. 포인트 사용시 포인트파트 팀원 메소드에 토스하기 (paymentNo, empNo, pointAmount, pointType


        // 5. 시간되면 주문내역 뿌리기

        return "/consumer/payment/pay-success";
    }


}

