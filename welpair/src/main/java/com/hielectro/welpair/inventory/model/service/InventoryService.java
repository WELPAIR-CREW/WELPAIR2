package com.hielectro.welpair.inventory.model.service;


import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.inventory.model.dto.StockDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InventoryService {

    /* 1-1 */
    int getTotalInventoryAmount();
    int getNumberOfAlertStock();

    /* 1-2 */
    List<ProductDTO> searchProductByCode(String searchCode);

    /* 2-1 */
    List<ProductDTO> stockRegistSerch(ProductDTO product);

    /* 2-2 */
    @Transactional
    int stockRegist(List<StockDTO> stockList);
}
