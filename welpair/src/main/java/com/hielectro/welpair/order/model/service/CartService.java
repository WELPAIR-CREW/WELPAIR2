package com.hielectro.welpair.order.model.service;

import com.hielectro.welpair.order.model.dto.CartGeneralDTO;

import java.util.List;

public interface CartService {
    List<CartGeneralDTO> cartAllInfoSelect(String empNo);

    int testCartAllInfoSelect(String empNo);
}
