package com.hielectro.welpair.delivery.model.service;

import org.springframework.stereotype.Service;

import com.hielectro.welpair.delivery.model.dao.adminOrderMapper;

@Service
public class adminOrderService {

    private final adminOrderMapper adminOrder;

    public adminOrderService(adminOrderMapper adminOrder)
    { this.adminOrder = adminOrder; }

}
