package com.hielectro.welpair.sales.model.service;

import com.hielectro.welpair.sales.model.dto.SalesDTO;
import java.util.List;

public interface SalesService {
    List<SalesDTO> getMonthSales(SalesDTO sales);
}
