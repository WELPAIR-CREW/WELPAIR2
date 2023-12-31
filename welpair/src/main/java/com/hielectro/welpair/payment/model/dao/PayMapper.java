package com.hielectro.welpair.payment.model.dao;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.dto.PointHistoryDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.payment.model.dto.PaymentDTO;
import com.hielectro.welpair.payment.model.dto.PointPayDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.ThumbnailImageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.util.List;

@Mapper
public interface PayMapper {


    List<MemberDTO> selectMemberById(String empNo);

    SellProductDTO selectProductById(String sellProductId);

    int insertOrder(OrderDTO order);


    int deleteOrder(String orderNo);

    int insertPayment(PaymentDTO item);

    int insertOrderPayment(String orderNo, String paymentNo);

    int insertProductOrder(ProductOrderDTO product);

    SellProductDTO selectProductCode(String id);

    Date selectOrderDate(String orderNo);

    int insertPointHistoryForUse(PointHistoryDTO pointHistory);

    int updateUsePointBalance(MemberDTO member);

    int insertPointPay(PointPayDTO pointPay);
}
