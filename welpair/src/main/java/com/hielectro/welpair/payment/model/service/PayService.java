package com.hielectro.welpair.payment.model.service;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.payment.model.dto.PaymentDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

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
}

