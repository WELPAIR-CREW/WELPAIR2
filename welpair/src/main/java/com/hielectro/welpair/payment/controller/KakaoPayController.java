package com.hielectro.welpair.payment.controller;

import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.payment.model.dto.KKPApproveResponse;
import com.hielectro.welpair.payment.model.dto.KKPReadyRequest;
import com.hielectro.welpair.payment.model.dto.KKPReadyResponse;
import com.hielectro.welpair.payment.model.service.KakaoPayService;
import com.hielectro.welpair.payment.model.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.hielectro.welpair.common.PriceCalculator.empNo;

@Slf4j
@Controller
@RequestMapping("/payment/kakaopay")
public class KakaoPayController {


    private final KakaoPayService kakaoPayService;

    private String tid;
    private String orderNo = "";


    public KakaoPayController(KakaoPayService kakaoPayService){
        this.kakaoPayService = kakaoPayService;
    }

    // 카카오페이결제 요청
    @GetMapping("do") // 리다이렉트된 주소이므로 getmapping
    public @ResponseBody KKPReadyResponse payReady(
            @ModelAttribute("order") OrderDTO order
//          , @RequestParam(required = false) String item_name
            , Model model
    ) {


        log.info("주문정보:" + order);
        String item_name = order.getProductOrderList().get(0).getSellProductId();

        // 결제 실패, 취소시 테이블 삭제할 orderNo 미리 저장해둠
        orderNo = order.getOrderNo();


        // 카카오 결제 준비하기	- 결제요청 service 실행.
        log.info("================={} ", kakaoPayService);

        // 결제준비 데이터 셋팅
        kakaoPayService.setReadyRequestData(order, item_name);
        // 데이터 셋팅후 준비 응답객체 반환받아옴
        KKPReadyResponse readyResponse = kakaoPayService.payReady();

        // 요청처리후 받아온 응답객체의 결재고유 번호(tid)를 모델에 저장
        model.addAttribute("tid", readyResponse.getTid());
        tid = readyResponse.getTid();
        log.info("결재고유 번호: " + readyResponse.getTid());

        // Order정보를 모델에 저장
        model.addAttribute("order",order);

        return readyResponse; // 클라이언트에 보냄.(tid,next_redirect_pc_url이 담겨있음.)
    }

    // 결제승인요청
    @GetMapping("/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken,
                               @ModelAttribute("order") OrderDTO order
                               , RedirectAttributes rttr) {

        log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
        log.info("주문정보: " + order);
        log.info("결재고유 번호: " + tid);

        // 결제 요청데이터 세팅
        kakaoPayService.setApproveRequestData(tid, pgToken);
        // 카카오 결제 요청하고 응답객체 받아오기
        KKPApproveResponse approveResponse = kakaoPayService.payApprove();

        System.out.println("approveResponse ======================== " + approveResponse);

        // order 객체에 tid까지 저장 후 데이터 넘겨줌 (success 매핑에서 데이터 저장할 거임)
        order.getOrderPayment().getPaymentList().stream()
                .filter(item -> item.getPaymentType().contains("카카오페이"))
                .forEach(item -> item.setTid(tid));

        rttr.addFlashAttribute("order", order);

        return "redirect:payment/pay-success";
    }
    // 결제 취소시 실행 url
    @GetMapping("/cancel")
    public String payCancel(RedirectAttributes rttr) {

        // order 테이블 삭제해야함
        rttr.addFlashAttribute("orderNo", orderNo);

        return "redirect:/payment/pay-fail";
    }

    // 결제 실패시 실행 url
    @GetMapping("/fail")

    public String payFail(RedirectAttributes rttr) {

        // order 테이블 삭제해야함
        rttr.addFlashAttribute("orderNo", orderNo);

        return "redirect:/payment/pay-fail";
    }
}