package com.hielectro.welpair.delivery.model.service;

import com.hielectro.welpair.delivery.model.dao.adminOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class adminOrderService {

    private final adminOrderMapper adminOrder;

    public adminOrderService(adminOrderMapper adminOrder)
    { this.adminOrder = adminOrder; }

}
