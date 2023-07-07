package com.hielectro.welpair.payment.model.service;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;
import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;

import java.util.List;


public interface PayService {

    List<MemberDTO> selectMemberById(String empNo);
}

