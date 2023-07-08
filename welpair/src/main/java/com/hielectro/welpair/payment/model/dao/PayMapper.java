package com.hielectro.welpair.payment.model.dao;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.mypage.model.dto.AddressDTO;
import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayMapper {


    List<MemberDTO> selectMemberById(String empNo);

    SellProductDTO selectProductById(String sellProductId);

    int insertOrder(OrderDTO order);


}
