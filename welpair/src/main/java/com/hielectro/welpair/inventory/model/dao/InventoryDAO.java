package com.hielectro.welpair.inventory.model.dao;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.inventory.model.dto.StockDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface InventoryDAO {

    /* 1-1 */
    int getTotalInventoryAmount();

    int getNumberOfAlertStock();

    /* 1-2 */
    List<ProductDTO> searchProductByCode(String searchCode);


    /* 2-1 */
    List<ProductDTO> stockRegistSerch(@Param("productCode") String productCode,
                                      @Param("productName") String productName,
                                      @Param("categoryName") String categoryName);



    List<StockDTO> searchAllStock();

}
