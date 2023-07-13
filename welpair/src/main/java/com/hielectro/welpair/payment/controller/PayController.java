
package com.hielectro.welpair.payment.controller;

import com.hielectro.welpair.inventory.model.dto.StockDTO;
import com.hielectro.welpair.inventory.model.service.InventoryService;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.dto.PointHistoryDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.order.model.service.CartService;
import com.hielectro.welpair.payment.model.dto.OrderPayReqDTO;
import com.hielectro.welpair.payment.model.dto.PaymentDTO;
import com.hielectro.welpair.payment.model.dto.PointPayDTO;
import com.hielectro.welpair.payment.model.service.PayService;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@PreAuthorize("hasRole('MEMBER')")
@RequestMapping({"/payment"})
public class PayController {

    private final PayService payService;
    private final CartService cartService;

    private final InventoryService inventoryService;

    private PayController(PayService payService, CartService cartService, InventoryService inventoryService) {
        this.payService = payService;
        this.cartService = cartService;
        this.inventoryService = inventoryService;
    }

    @PostMapping("/payment.do")
    public String gotopay(@ModelAttribute("orderPrdList") OrderPayReqDTO orderPrdList, Model model
                                      , @AuthenticationPrincipal User user
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
        System.out.println("empNo=================" + user.getUsername());
        List<MemberDTO> memberAddressList = payService.selectMemberById(user.getUsername());
        System.out.println(memberAddressList);
        model.addAttribute("memberAddressList", memberAddressList);

        return "/consumer/payment/payment";
    }

    // 결제수단에 따른 매핑 나누기
    @PostMapping("/payment.go")
    public String gotopay(@RequestBody OrderDTO order, RedirectAttributes rttr, ModelAndView model
                                      , @AuthenticationPrincipal User user
    ) throws Exception {

        log.info("리다이렉트용 매핑 컨트롤러 들어옴");

        // ORDER테이블에 데이터 넣어서 주문번호 orderNo 바로 받아오기
        order.setMemberNo(user.getUsername());  // 회원 아이디, 나중에 로그인 열기
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

//           model.setViewName("redirect:/payment/pay-success");
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
    public String paySuccess(@ModelAttribute("order") OrderDTO order, RedirectAttributes rttr
                , @AuthenticationPrincipal User user
    ) throws Exception {

        log.info("pay-success 매핑들어옴===> " + order);
        // 1. payment 테이블 insert // paymentNo 가져옴 ---> 2. orderPayment 테이블 insert 동시진행

        // orderNo 미리 셋팅
        order.getOrderPayment().setOrderNo(order.getOrderNo());

        // tid가 null이면 테이블 데이터삽입시 오류가 생겨서 강제로 none 문자열값을 넣어준다.
        order.getOrderPayment().getPaymentList().stream()
                .filter(item -> item.getPaymentType().contains("복지"))
                .forEach(item -> item.setTid("NONE"));

        // pointpay dto (테이블 데이터 삽입용)  --- 카카오페이는 롤백안되게 다시집어넣어
        PointPayDTO pointPay = new PointPayDTO();

        order.getOrderPayment().getPaymentList().forEach(item -> {
                    log.info("item : " + item);
                    try {


                        payService.insertPayment(item); // tid
                        log.info("paymentNo : " + item.getPaymentNo() + " orderNo : " + order.getOrderNo());
                        payService.insertOrderPayment(order.getOrderNo(), item.getPaymentNo());

                        if(item.getPaymentType().contains("복지")){
                            pointPay.setPaymentNo(item.getPaymentNo());
                        }

                    } catch (SQLTransactionRollbackException e) {
                        System.out.println("!!!!!!!!!!pay insert 실패!!!!!!!!!");
                        payService.deleteOrder(order.getOrderNo());
                    }
                }
        );

        // 3. productorder list 테이블 insert
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

        cartService.deleteCartProduct((ArrayList<String>) order.getProductOrderList()
                .stream().map(item -> item.getSellProductId()).collect(Collectors.toList()), user.getUsername());

        // 4. 자동 출고 등록
        List<StockDTO> stockList = new ArrayList<>();
        StockDTO stock = stockOutManager(order);
        stockList.add(stock);
        log.info("stockList", stockList);
        int resultStock = inventoryService.stockRegist(stockList);
        if(resultStock != stockList.size()){
            log.info("결과값 result : " + resultStock + "출고등록 실패");
            inventoryService.stockRegist(stockList);
        }


        // 5. 포인트 사용시
        PointHistoryDTO pointHistory = pointUserManager(order, stockList, user.getUsername());

        MemberDTO member = new MemberDTO();
        member.setEmpNo(user.getUsername());
        member.setPointBalance(pointHistory.getPointAmount());

        int resultPoint = payService.insertPointHistoryForUse(pointHistory);


        if(resultPoint > 0 ){

            pointPay.setPointNo(pointHistory.getPointNo());

            log.info("포인트 차감 데이터 삽입 성공 후 잔액 업데이트");
            int resultBalance = payService.updateUsePointBalance(member);


            if(resultBalance < 0) {
                log.warn("포인트잔액 업데이트 실패 재시도 확인");
                payService.updateUsePointBalance(member);
            }

        } else {

            log.warn("포인트 차감 데이터 삽입 실패 재시도 확인");
            payService.insertPointHistoryForUse(pointHistory);

        }

        // 6. insertPointPay 테이블 삽입
        int resultPointPay = payService.insertPointPay(pointPay);

        if(resultPointPay <= 0 ){
            log.warn("포인트페이 테이블 데이터 삽입 실패 재시도 확인");
            payService.insertPointPay(pointPay);

        }

        // 카카오페이 리다이렉트
        if(order.getOrderPayment().getPaymentList().stream()
                .anyMatch(i -> i.getPaymentType().contains("카카오페이"))){
            return "redirect:/mypage/myorder/detail/" + order.getOrderNo();
        }

        // 복지포인트 리다이렉트
        rttr.addFlashAttribute("orderNo", order.getOrderNo());
        return "redirect:/payment/point-use-redirect";
    }

    // 복지포인트 리다이렉트
    @GetMapping("point-use-redirect")
    @ResponseBody
    public String pointUseRedirect(@ModelAttribute("order") OrderDTO order, @ModelAttribute("orderNo") String orderNo){

        System.out.println("order222222 = " + order);
        return orderNo;
    }

    // 출고관리 매니저
    public StockDTO stockOutManager(OrderDTO order){

        StockDTO stock = new StockDTO();
        SellProductDTO sellProduct = new SellProductDTO();

        for(ProductOrderDTO product : order.getProductOrderList()){

            sellProduct = payService.selectProductCode(product.getSellProductId());
            stock.setStockType("출고");
            stock.setProductCode(sellProduct.getCode());
            stock.setStockDate(payService.selectOrderDate(product.getOrderNo()));
            stock.setStockAmount(-product.getProductOrderAmount());
            stock.setProductAmount(sellProduct.getProduct().getProductAmount());
            stock.setStockComment("일반주문");

        }
        log.info("stock 확인 ====> ", stock);

        return stock;
    }

    // 포인트 사용 관리 매니저
    public PointHistoryDTO pointUserManager(OrderDTO order, List<StockDTO> stockList, String userName){

        PointHistoryDTO pointHistory = new PointHistoryDTO();

        pointHistory.setEmpNo(userName);

        int usePoint = order.getOrderPayment().getPaymentList()
                .stream().filter(item -> item.getPaymentType().contains("복지"))
                .mapToInt(PaymentDTO::getPaymentPrice).sum();

        System.out.println("usePoint 출력 확인= " + usePoint);

        pointHistory.setPointDate(stockList.get(0).getStockDate());
        pointHistory.setPointAmount(usePoint);
        System.out.println("복지사용포인트 확인 : " + pointHistory.getPointAmount());
        pointHistory.setPointType("사용");
        pointHistory.setPointReason("주문결제");
        pointHistory.setEventId(0);

        System.out.println("pointHistory 출력 확인= " + pointHistory);

        return pointHistory;
    }


}