package com.hielectro.welpair.payment.model.service;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.payment.model.dao.PayMapper;
import com.hielectro.welpair.payment.model.dto.PaymentDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.ThumbnailImageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLTransactionRollbackException;
import java.util.List;

@Slf4j
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
    public boolean deleteOrder(String orderNo) {
        return payMapper.deleteOrder(orderNo) > 0 ? true : false;
    }

    @Transactional
    @Override
    public void insertPayment(PaymentDTO item) throws SQLTransactionRollbackException {

        if (payMapper.insertPayment(item) < 0){
            throw new SQLTransactionRollbackException();
        };

    }

    @Transactional
    @Override
    public void insertOrderPayment(String paymentNo, String orderNo) throws SQLTransactionRollbackException {

        if(payMapper.insertOrderPayment(paymentNo, orderNo) < 0 ){
            throw new SQLTransactionRollbackException();
        }
    }

    @Transactional
    @Override
    public void insertProductOrder(ProductOrderDTO product) throws SQLTransactionRollbackException {

        if(payMapper.insertProductOrder(product) < 0) {

            throw new SQLTransactionRollbackException();
        }
    }


}
