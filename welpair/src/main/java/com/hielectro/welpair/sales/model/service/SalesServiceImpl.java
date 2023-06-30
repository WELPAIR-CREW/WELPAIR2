package com.hielectro.welpair.sales.model.service;

import com.hielectro.welpair.sales.model.dao.SalesDAO;
import org.springframework.stereotype.Service;

@Service
public class SalesServiceImpl implements SalesService {

    private final SalesDAO salesDAO;

    public SalesServiceImpl(SalesDAO salesDAO) {
        this.salesDAO = salesDAO;
    }
}
