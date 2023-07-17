package com.hielectro.welpair.mypage.model.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
