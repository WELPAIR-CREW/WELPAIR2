package com.hielectro.welpair.payment.model.service;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;
import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


public interface PayService {

    List<MemberDTO> selectMemberById(String empNo);

    SellProductDTO selectProductById(String sellProductId);

    boolean insertOrder(OrderDTO order);


}

