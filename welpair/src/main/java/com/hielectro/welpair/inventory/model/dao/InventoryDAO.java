package com.hielectro.welpair.inventory.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.inventory.model.dto.StockDTO;
@Mapper
public interface InventoryDAO {

    /* 1-1 */
    int getTotalInventoryAmount();

    int getNumberOfAlertStock();

    /* 1-2 */
    List<ProductDTO> searchProductByCode(String searchCode);

    /* 2-1 */
    List<ProductDTO> stockRegistSerch(ProductDTO product);

    /* 2-2 */
    int stockRegist(StockDTO stock);

    /* 3-1 */
    List<StockDTO> historySearch(StockDTO stock);

    void stockRegistUpdate(String productCode, int stockAmount);
}
