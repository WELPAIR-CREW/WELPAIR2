package com.hielectro.welpair.main.controller;

import com.hielectro.welpair.main.model.service.MainService;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDetailDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping({"consumer/header_consumer", "admin/header_admin", "admin/index"})
    public void header() {}

    @GetMapping({"/", "index"})
    public String defaultConsumerPage(Model model) {
        List<SellProductDetailDTO> list = mainService.selectProductList();
        model.addAttribute("list", list);
        return "index";
    }

    @PostMapping({"/", "index"})
    public String postTest(Model model) {
        List<SellProductDetailDTO> list = mainService.selectProductList();
        model.addAttribute("list", list);
        return "index";
    }

    @RequestMapping({"admin/footer", "consumer/footer"})
    public void footer() {}

    @GetMapping("products/{pageNo}")
    public String productPage(@PathVariable String pageNo, Model model) {
        SellProductDTO sellProduct = mainService.selectOneSellProduct(pageNo);
        System.out.println(sellProduct);
        model.addAttribute("sellProduct", sellProduct);
        return "consumer/sellproduct/product-detail";
    }
}
