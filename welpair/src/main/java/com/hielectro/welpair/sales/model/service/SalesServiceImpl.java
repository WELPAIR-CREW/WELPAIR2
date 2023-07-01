package com.hielectro.welpair.sales.model.service;

import com.hielectro.welpair.sales.model.dto.SalesDTO;
import com.hielectro.welpair.sales.model.dao.SalesDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {

    private final SalesDAO salesDAO;

    public SalesServiceImpl(SalesDAO salesDAO) {
        this.salesDAO = salesDAO;
    }

    @Override
    public List<SalesDTO> getMonthSales() {
        System.out.println("========== 매출 서비스 1-1 in ==========");

        System.out.println("========== 매출 서비스 1-1 out ==========");
        return salesDAO.getMonthSales();
    }
}
