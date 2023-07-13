package com.hielectro.welpair.mypage.model.dto;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.payment.model.dto.PaymentDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailDTO {

    private String orderNo;
    private Timestamp orderDate;
    private int totalPrice;

    private String orderType;

    private String productName;
    private int productOrderAmount;
    private int price;


    private String paymentType;
    private int kakaoPay;
    private int point;
    private int paymentPrice;

    private String deliveryStatus;

    private String addressDetail;
    private String addressName;
    private String addressPhone;
    private Date deliveryDate;

    private List<Map<String, ProductDTO>> productList;

}
