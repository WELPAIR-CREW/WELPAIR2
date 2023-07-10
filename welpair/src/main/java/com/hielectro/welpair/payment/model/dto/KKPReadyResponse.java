package com.hielectro.welpair.payment.model.dto;


import com.hielectro.welpair.order.model.dto.OrderDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KKPReadyResponse {


    private String tid;                      // 결제 고유번호, 결제 준비 API 응답에 포함
    private String next_redirect_pc_url;        // 요청한 클라이언트가 PC 웹일 경우카카오톡으로

                                                // 결제 요청 메시지(TMS)를 보내기 위한 사용자 정보 입력 화면 Redirect URL

    private OrderDTO order;

}
