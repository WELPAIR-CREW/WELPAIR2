package com.hielectro.welpair.inventory.model.service;


import java.util.List;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.inventory.model.dto.StockDTO;

public interface InventoryService {

    /* 1-1 */
    int getTotalInventoryAmount();
    int getNumberOfAlertStock();

    /* 1-2 */
    List<ProductDTO> searchProductByCode(String searchCode);

    /* 2-1 */
    List<ProductDTO> stockRegistSerch(ProductDTO product);

    /* 2-2 */
    int stockRegist(List<StockDTO> stockList);

    /* 3-1 */
    List<StockDTO> historySearch(StockDTO stock);
}
