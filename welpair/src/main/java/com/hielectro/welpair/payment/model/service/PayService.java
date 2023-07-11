package com.hielectro.welpair.payment.model.service;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.member.model.dto.PointHistoryDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.payment.model.dto.PaymentDTO;
import com.hielectro.welpair.payment.model.dto.PointPayDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.ThumbnailImageDTO;

import java.sql.Date;
import java.sql.SQLTransactionRollbackException;
import java.util.List;


public interface PayService {

    List<MemberDTO> selectMemberById(String empNo);

    SellProductDTO selectProductById(String sellProductId);

    boolean insertOrder(OrderDTO order);


    boolean deleteOrder(String orderNo);


    void insertPayment(PaymentDTO item) throws SQLTransactionRollbackException;

    void insertOrderPayment(String paymentNo, String orderNo) throws SQLTransactionRollbackException;

    void insertProductOrder(ProductOrderDTO product) throws SQLTransactionRollbackException;

    SellProductDTO selectProductCode(String id);

    Date selectOrderDate(String orderNo);


    int insertPointHistoryForUse(PointHistoryDTO pointHistory);

    int updateUsePointBalance(MemberDTO member);

    int insertPointPay(PointPayDTO pointPay);
}

