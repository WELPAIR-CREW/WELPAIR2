package com.hielectro.welpair.mypage.model.dto;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailDTO {

    private String orderNo;
    private Timestamp orderDate;
    private int totalPrice;
    private int kakaoPay;
    private int point;

    private String orderType;
    private String deliveryStatus;

    private String addressDetail;
    private String addressName;
    private String addressPhone;


    private List<ProductOrderDTO> productList;



}
