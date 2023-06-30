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

        System.out.println("========== 서비스 1-2 in ==========");
        System.out.println("searchCode = " + searchCode);
        System.out.println("========== 서비스 1-2 out ==========");
        return inventoryDAO.searchProductByCode(searchCode);

    }

    /* 2-1 */
    @Override
    public List<ProductDTO> stockRegistSerch(ProductDTO product) {
        System.out.println("========== 서비스 2-1 in ==========");
        System.out.println("productCode = " + product.getProductCode());
        System.out.println("productName = " + product.getProductName());
        System.out.println("categoryCode = " + product.getCategoryCode());
        System.out.println("productAmount = " + product.getProductAmount());

        List<ProductDTO> result = inventoryDAO.stockRegistSerch(product);
        System.out.println("========== 서비스 2-1 out ==========");

        return result;
    }

    /* 2-2 */
    @Override
    public int stockRegist(List<StockDTO> stockList) {

        int result = 0;
        ProductDTO product = new ProductDTO();

        System.out.println("========== 서비스 2-2 in ==========");
        System.out.println("stockList = " + stockList);


        System.out.println("for문 준비중");
        for (StockDTO stock : stockList) {
            System.out.println("for문 시작");
            product.setProductAmount(stock.getProductAmount());

            if ("출고".equals(stock.getStockType())) {
                System.out.println("출고임");
                if (-(stock.getStockAmount()) > stock.getProductAmount()) {

                    System.out.println("출고 수량 부족. 가능 수량 : " + stock.getProductAmount());
                    System.out.println("========== 서비스 2-2 등록 실패 ==========");
                    return -1;

                } else {
                    System.out.println("수량 넉넉해");
                    int num = inventoryDAO.stockRegist(stock);
                    result += num;
                }
            } else {
                System.out.println("출고 아님");
                int num = inventoryDAO.stockRegist(stock);
                result += num;
            }
            System.out.println("========== 서비스 2-2 완료 ==========");
        }

        if (result == stockList.size()) {
            for (StockDTO stock : stockList) {
                inventoryDAO.stockRegistUpdate(stock.getProductCode(), stock.getStockAmount());
            }
            System.out.println("========== 서비스 2-2 out ==========");
            return result;
        }else {
            return 0;
        }
    }

    /* 3-1 */
    @Override
    public List<StockDTO> historySearch(StockDTO stock) {
        System.out.println("========== 서비스 3-1 in ==========");

        if(stock.getProduct() != null){
            System.out.println("productName = " + stock.getProduct().getProductName());
            System.out.println("productAmount = " + stock.getProduct().getProductAmount());
        } else{}

        System.out.println("stockNo = " + stock.getStockNo());
        System.out.println("productCode = " + stock.getProductCode());
        System.out.println("stockType = " + stock.getStockType());
        System.out.println("startDate = " + stock.getStartDate());
        System.out.println("endDate = " + stock.getEndDate());
        System.out.println("stockAmount = " + stock.getStockAmount());
        System.out.println("stockComment = " + stock.getStockComment());

        List<StockDTO> result = inventoryDAO.historySearch(stock);
        System.out.println("========== 서비스 3-1 out ==========");

        return result;
    }
}
