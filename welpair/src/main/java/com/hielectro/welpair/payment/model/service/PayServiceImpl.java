package com.hielectro.welpair.payment.model.service;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;
import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.payment.model.dao.PayMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceImpl implements PayService {

    private final PayMapper payMapper;

    public PayServiceImpl(PayMapper payMapper) {
        this.payMapper = payMapper;
    }


    @Override
    public List<MemberDTO> selectMemberById(String empNo) {
        return payMapper.selectMemberById(empNo);
    }
}
