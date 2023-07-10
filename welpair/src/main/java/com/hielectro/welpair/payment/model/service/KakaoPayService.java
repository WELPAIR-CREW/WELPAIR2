package com.hielectro.welpair.payment.model.service;

import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.payment.model.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class KakaoPayService {

    private final KKPReadyRequest readyRequest = new KKPReadyRequest();
    private final KKPApproveRequest approveRequest = new KKPApproveRequest();


    public KKPReadyResponse payReady(OrderDTO order) {

        // request 값 담기
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

        param.add("cid", readyRequest.getCid());    // 가맹점 코드(테스트용 코드)
        param.add("partner_order_id", readyRequest.getPartner_order_id());
        param.add("partner_user_id", readyRequest.getPartner_user_id());
        param.add("item_name", readyRequest.getItem_name());
        param.add("quantity", String.valueOf(readyRequest.getQuantity()));
        param.add("total_amount", String.valueOf(readyRequest.getTotal_amount()));
        param.add("tax_free_amount", String.valueOf(readyRequest.getTax_free_amount()));
        param.add("approval_url", readyRequest.getApproval_url());
        param.add("cancel_url", readyRequest.getCancel_url());
        param.add("fail_url", readyRequest.getFail_url());
        param.add("order", String.valueOf(readyRequest.getOrder()));

        // 카카오에서 요구하는 요청 파라미터, 헤더 정보들  requestEntity에 전부 담기
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(param, this.getHeaders());
        // 외부url요청 통로 열기
        RestTemplate template = new RestTemplate();
        // 카카오페이 결제준비 요청 url
        String url = "https://kapi.kakao.com/v1/payment/ready";

        log.info("requestEntity==" + requestEntity);

        // rest template을 통해 post 타입으로 1.요청url로 2.데이터 보내고 3. 반환 응답객체(ready response) 받아오기
        // postForObject(String url, @Nullable Object request, Class<T> responseType)
        KKPReadyResponse readyResponse = template.postForObject(url, requestEntity, KKPReadyResponse.class);

        log.info("결제준비 응답객체 받아옴 : " + readyResponse);

        return readyResponse;
    }


    public KKPApproveResponse payApprove() {

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("cid", approveRequest.getCid());
        param.add("tid", approveRequest.getTid());
        param.add("partner_order_id", approveRequest.getPartner_order_id());
        param.add("partner_user_id", approveRequest.getPartner_user_id());
        param.add("pg_token", approveRequest.getPg_token());
        param.add("order", String.valueOf(approveRequest.getOrder()));

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(param, this.getHeaders());
        RestTemplate template = new RestTemplate();
        // 결제 승인 요청 url
        String url = "https://kapi.kakao.com/v1/payment/approve";

        KKPApproveResponse approveResponse = template.postForObject(url, requestEntity, KKPApproveResponse.class);
        log.info("결재승인 응답객체 받아옴 ===" + approveResponse);

        return approveResponse;
    }


    // header
    private HttpHeaders getHeaders(){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 82c0874f050a15a925183dcae45c3a61");  // kakao admin key 띄어쓰기 주의
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return headers;
    }


    public void setReadyRequestData(OrderDTO order, String itemName) {

        readyRequest.setQuantity(order.getProductOrderList().size());

        readyRequest.setCid("TC0ONETIME");
        readyRequest.setPartner_order_id(order.getOrderNo());
        readyRequest.setPartner_user_id(order.getMemberNo());

        readyRequest.setTotal_amount(order.getOrderPayment().getPaymentList().stream()
                                    .filter(item->item.getPaymentType().contains("카카오페이"))
                                            .mapToInt(PaymentDTO::getPaymentPrice).sum());

        readyRequest.setItem_name(itemName + "외 " + readyRequest.getQuantity() + "건");
        readyRequest.setItem_code(order.getProductOrderList().get(0).getSellProductId() + "외" + readyRequest.getQuantity() + "건");
        readyRequest.setTax_free_amount(0);

        readyRequest.setApproval_url("http://localhost:8888/payment/kakaopay/completed");
        readyRequest.setCancel_url("http://localhost:8888/payment/kakaopay/cancel");
        readyRequest.setFail_url("http://localhost:8888/payment/kakaopay/fail");
        readyRequest.setOrder(order);

    }

    public void setApproveRequestData(String tid, String pg_token){

        approveRequest.setCid(readyRequest.getCid());
        approveRequest.setTid(tid);
        approveRequest.setPartner_order_id(readyRequest.getPartner_order_id());
        approveRequest.setPartner_user_id(readyRequest.getPartner_user_id());
        approveRequest.setPg_token(pg_token);

        approveRequest.setOrder(readyRequest.getOrder());
    }




}
