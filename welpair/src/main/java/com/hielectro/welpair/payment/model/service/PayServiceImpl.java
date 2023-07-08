package com.hielectro.welpair.payment.model.service;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;
import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.payment.model.dao.PayMapper;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class PayServiceImpl implements PayService {

    private final PayMapper payMapper;

    public PayServiceImpl(PayMapper payMapper) {
        this.payMapper = payMapper;
    }



    @Override
    public SellProductDTO selectProductById(String sellProductId) {
        return payMapper.selectProductById(sellProductId);
    }

    @Transactional
    @Override
    public boolean insertOrder(OrderDTO order) {


        return payMapper.insertOrder(order) > 0 ? true : false;
    }


    @Override
    public List<MemberDTO> selectMemberById(String empNo) {
        return payMapper.selectMemberById(empNo);
    }

}
