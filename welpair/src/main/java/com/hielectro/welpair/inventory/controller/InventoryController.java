package com.hielectro.welpair.inventory.controller;


import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.inventory.model.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/inventory/")

public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    /**
     * 재고관리 메뉴 (ng)
     * 1. 재고현황 페이지
     * 1-1. 상단부 현재기준 현황 출력 (총 재고수량, 위험재고 상품수)
     */
    @GetMapping("/getInventoryInfo")
    @ResponseBody
    public Map<String, Integer> getInvenInfo(Model model){
        System.out.println("-------------컨트롤러 1-1 -------------");

        int totalInvenAmount = inventoryService.getTotalInventoryAmount();
        int alertStock = inventoryService.getNumberOfAlertStock();

        Map<String, Integer> inventoryInfo = new HashMap<>();
        inventoryInfo.put("totalInvenAmount", totalInvenAmount);
        inventoryInfo.put("alertStock", alertStock);

        System.out.println("totalInvenAmount = " + totalInvenAmount);
        System.out.println("alertStock = " + alertStock);

        return inventoryInfo;
    }

    /**
     * 재고관리 메뉴 (ng)
     * 1. 재고현황 페이지
     * 1-2. 하단부 상품 코드 검색 시 해당 상품의 간단한 정보 출력
     */

    @GetMapping("admin_inventory")
    public String searchProductByCode(Model model, @RequestParam(required = false) String searchCode) {

        System.out.println("-------------컨트롤러 1-2 -------------");
        System.out.println("searchCode = " + searchCode);

        if(searchCode != null){
            List<ProductDTO> productList = inventoryService.searchProductByCode(searchCode);
            System.out.println("==================== 1-2 =============");
            model.addAttribute("productList", productList);
        } else {
            model.addAttribute("productList", Collections.emptyList());
        }

        return "admin/inventory/admin_inventory";
    }


    /**
     * 재고관리 메뉴 (ng)
     * 2. 입출고등록 페이지
     * 2-1. 등록할 상품 검색
     *      상품코드, 상품명, 카테고리 선택 후 검색 시 등록 대상 리스트 출력
     */
    @GetMapping("admin_inventory_register")
    public String stockRegistSerch(Model model,@ModelAttribute ProductDTO product) {
        try {

            String productCode = product.getProductCode();
            String productName = product.getProductName();
            String categoryCode = product.getCategoryCode();
            System.out.println("-------------컨트롤러 2-1 -------------");
            System.out.println("productCode = " + product.getProductCode());
            System.out.println("productName = " + product.getProductName());
            System.out.println("categoryCode = " + product.getCategoryCode());

            if (productCode != null || productName != null || categoryCode != null) {
//                List<ProductDTO> stockList = inventoryService.stockRegistSerch(productCode, productName, categoryCode);
                List<ProductDTO> stockList = inventoryService.stockRegistSerch(product);
                model.addAttribute("stockList", stockList);

                //System.out.println("------ 컨트롤러 back ------");
                System.out.println("stockList = " + stockList);
            } else {
                model.addAttribute("stockList", Collections.emptyList());
            }
            } catch(TemplateInputException e){
                e.printStackTrace();
            }
            return "admin/inventory/admin_inventory_register";
        }

    /**
     * 재고관리 메뉴 (ng)
     * 2. 입출고등록 페이지
     * 2-2. 입출고 등록
     */







    }

