package com.hielectro.welpair.inventory.controller;
import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.inventory.model.dto.StockDTO;
import com.hielectro.welpair.inventory.model.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.*;

@Controller
@Slf4j
@RequestMapping("/inventory/")
public class InventoryController {
    private final InventoryService inventoryService;
    private final MessageSource messageSource;

    @Autowired
    public InventoryController(InventoryService inventoryService, MessageSource messageSource) {
        this.inventoryService = inventoryService;
        this.messageSource = messageSource;
    }

    /**
     * 재고관리 메뉴 (ng) 1. 재고현황 페이지
     * 1-1. 상단부 현재기준 현황 출력 (총 재고수량, 위험재고 상품수)
     */
    @GetMapping("/getInventoryInfo")
    @ResponseBody
    public Map<String, Integer> getInvenInfo(){
        System.out.println("-------------컨트롤러 1-1 in -------------");

        int totalInvenAmount = inventoryService.getTotalInventoryAmount();
        int alertStock = inventoryService.getNumberOfAlertStock();

        Map<String, Integer> inventoryInfo = new HashMap<>();
        inventoryInfo.put("totalInvenAmount", totalInvenAmount);
        inventoryInfo.put("alertStock", alertStock);

        System.out.println("totalInvenAmount = " + totalInvenAmount);
        System.out.println("alertStock = " + alertStock);
        System.out.println("-------------컨트롤러 1-1 out -------------");

        return inventoryInfo;
    }

    /**
     * 재고관리 메뉴 (ng) 1. 재고현황 페이지
     * 1-2. 하단부 상품 코드 검색 시 해당 상품의 간단한 정보 출력
     */
    @GetMapping("admin_inventory")
    public String searchProductByCode(Model model, @RequestParam(required = false) String searchCode) {

        System.out.println("-------------컨트롤러 1-2 in -------------");
        System.out.println("searchCode = " + searchCode);

        if(searchCode != null){
            List<ProductDTO> productList = inventoryService.searchProductByCode(searchCode);
            model.addAttribute("productList", productList);
        System.out.println("-------------컨트롤러 1-2 out -------------");
        } else {
            model.addAttribute("productList", Collections.emptyList());
        }

        return "admin/inventory/admin_inventory";
    }


    @GetMapping("admin_inventory_register")
    public String stockRegistSerch() {
            System.out.println("-------------컨트롤러 2-1-1 in -------------");
            System.out.println("-------------컨트롤러 2-1-1 out-------------");
    return "admin/inventory/admin_inventory_register";
}

    /**
     * 재고관리 메뉴 (ng) 2. 입출고등록 페이지
     * 2-1. 등록할 상품 검색
     *      상품코드, 상품명, 카테고리 선택 후 검색 시 등록 대상 리스트 출력
     */
    @PostMapping("admin_inventory_register")
    @ResponseBody
    public List<ProductDTO> stockRegistSerch1(@ModelAttribute ProductDTO product) {
        List<ProductDTO> stockList = null;
        try {

            String productCode = product.getProductCode();
            String productName = product.getProductName();
            String categoryCode = product.getCategoryCode();
            System.out.println("-------------컨트롤러 2-1-2 in -------------");
            System.out.println("productCode = " + product.getProductCode());
            System.out.println("productName = " + product.getProductName());
            System.out.println("categoryCode = " + product.getCategoryCode());

            if (productCode != null || productName != null || categoryCode != null) {
                stockList = inventoryService.stockRegistSerch(product);
            System.out.println("-------------컨트롤러 2-1-2 out -------------");
            } else {
            }
        } catch(TemplateInputException e){
            e.printStackTrace();
        }
        return stockList;
    }

    /**
     * 재고관리 메뉴 (ng)
     * 2. 입출고등록 페이지
     * 2-2. 입출고 등록
     */
    @PostMapping("stockRegist")
    public ModelAndView stockRegist (ModelAndView mv, @RequestBody List<StockDTO> stockList
                                    , RedirectAttributes rttr, Locale locale){

        System.out.println("-------------컨트롤러 2-2 in -------------");
        System.out.println("stockList = " + stockList);
        System.out.println("locale = " + locale);
        int result = inventoryService.stockRegist(stockList);

        System.out.println("result = " + result);
        if(result > 0){

            rttr.addFlashAttribute("successMessage", "입출고 등록 성공");
            System.out.println("-------------컨트롤러 2-2 out -------------");
        } else {
            rttr.addFlashAttribute("failMessage", "입출고 등록 실패");
        }

        mv.setViewName("redirect:/inventory/admin_inventory_register");

        return mv;

    }



    @GetMapping("admin_inventory_search")
    public String StockhistorySearch() {

            System.out.println("-------------컨트롤러 3-1-1 in -------------");
            System.out.println("-------------컨트롤러 3-1-1 out -------------");

        return "admin/inventory/admin_inventory_search";
    }

    /**
     * 재고관리 메뉴 (ng)
     * 3. 입출고내역
     * 3-1. 입출고내역 검색
     *      상품코드, 상품명, 카테고리 선택 후 검색 시 입출고 내역 조회
     */
    @PostMapping("admin_inventory_search")
    @ResponseBody
    public List<StockDTO> historySearch(Model model, @ModelAttribute StockDTO stock) {

        System.out.println("-------------컨트롤러 3-1-2 in -------------");
        System.out.println("stock = " + stock);
        System.out.println("stock.getProductCode() = " + stock.getProductCode());

        System.out.println("-------------컨트롤러 3-1-2 -------------");
        List<StockDTO> stockList = null;
        if (stock != null) {
            stockList = inventoryService.historySearch(stock);
            System.out.println("stockList = " + stockList);

            model.addAttribute("stockList", stockList);
            System.out.println("-------------컨트롤러 3-1-2 out -------------");
        } else {
            model.addAttribute("stockList", Collections.emptyList());
        }
        return stockList;
    }



}

