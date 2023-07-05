package com.hielectro.welpair.payment.model.service;

import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.payment.model.dao.PayMapper;
import com.hielectro.welpair.payment.model.dto.CartPayReqDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PayServiceImpl implements PayService {

    private final PayMapper payMapper;

    public PayServiceImpl(PayMapper payMapper) {
        this.payMapper = payMapper;
    }

    @Override
    public List<CartGeneralDTO> selectProductById(CartPayReqDTO cartPayList) {

        List<CartGeneralDTO> payList = new ArrayList<>();

        for(CartGeneralDTO cartPay : cartPayList.getCartPayList()){
            System.out.println();
            System.out.println("서비스 검색 상품 : " + cartPay.getCartSellProduct().getSellProductId());
            System.out.println();

            payList.add(payMapper.selectProductById(cartPay.getCartSellProduct().getSellProductId()));

        }
        return payList;
    }
}
