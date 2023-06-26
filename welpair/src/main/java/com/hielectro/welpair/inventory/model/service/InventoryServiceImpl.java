package com.hielectro.welpair.inventory.model.service;


import com.hielectro.welpair.inventory.model.dao.InventoryDAO;
import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.inventory.model.dto.StockDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService{

    private final InventoryDAO inventoryDAO;


    @Autowired
    public InventoryServiceImpl(InventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    /* 1-1 */
    @Override
    public int getTotalInventoryAmount() {
        return inventoryDAO.getTotalInventoryAmount();
    }

    @Override
    public int getNumberOfAlertStock() {
        return inventoryDAO.getNumberOfAlertStock();
    }

    /* 1-2 */
    @Override
    public List<ProductDTO> searchProductByCode(String searchCode){

        System.out.println("------------------- 서비스 1-2 -------------------");
        System.out.println("searchCode = " + searchCode);
        return inventoryDAO.searchProductByCode(searchCode);

    }

    /* 2-1 */
    @Override
    public List<ProductDTO> stockRegistSerch(String productCode, String productName, String categoryName) {
        System.out.println("------------------- 서비스 2-1 -------------------");
        System.out.println("productCode = " + productCode);
        System.out.println("productName = " + productName);
        System.out.println("categoryName = " + categoryName);

        return inventoryDAO.stockRegistSerch(productCode, productName, categoryName);
    }



    @Override
    public List<StockDTO> searchAllStock() {
        return inventoryDAO.searchAllStock();
    }
}
