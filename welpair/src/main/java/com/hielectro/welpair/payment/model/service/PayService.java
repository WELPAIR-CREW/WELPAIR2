package com.hielectro.welpair.payment.model.service;

import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;

import java.util.List;


public interface PayService {
    List<CartGeneralDTO> selectProductById(String sellProductId);
}
