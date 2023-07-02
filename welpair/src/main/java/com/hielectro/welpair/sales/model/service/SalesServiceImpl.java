package com.hielectro.welpair.sales.model.service;

import com.hielectro.welpair.inventory.model.dto.CategoryDTO;
import com.hielectro.welpair.payment.dto.PaymentDTO;
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
    public List<SalesDTO> getMonthSales(SalesDTO sales) {
        System.out.println("========== 매출 서비스 1-1 in ==========");

        String paymentType = sales.getPayment().getPaymentType();
        if(paymentType != null){
            salesDAO.paymentType(paymentType);
        }
        String categoryCode = sales.getCategory().getCategoryCode();
        if (categoryCode != null) {
            salesDAO.categoryCode(categoryCode);
        }
        List<SalesDTO> result = salesDAO.getMonthSales(sales);
        System.out.println("result = " + result);
        System.out.println("========== 매출 서비스 1-1 out ==========");
        return result;
    }

}
