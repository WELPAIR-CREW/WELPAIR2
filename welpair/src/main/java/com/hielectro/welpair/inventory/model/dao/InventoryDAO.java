package com.hielectro.welpair.inventory.model.dao;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.inventory.model.dto.StockDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
public interface InventoryDAO {

    /* 1-1 */
    int getTotalInventoryAmount();

    int getNumberOfAlertStock();

    /* 1-2 */
    List<ProductDTO> searchProductByCode(String searchCode);

    /* 2-1 */
    List<ProductDTO> stockRegistSerch(ProductDTO product);
//    List<ProductDTO> stockRegistSerch(String productCode, String productName, String refCategoryName, String categoryName);



    List<StockDTO> searchAllStock();

}
