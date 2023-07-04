package com.hielectro.welpair.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping({"consumer/header_consumer", "admin/header_admin", "admin/index", "index"})
    public void header() {}

    @RequestMapping({"admin/footer", "consumer/footer"})
    public void footer() {}

    @GetMapping("products/{pageNo}")
    public String productPage(@PathVariable String pageNo, Model model) {

        model.addAttribute("sellProductPageNo", pageNo);
        return "consumer/sellproduct/product-detail";
    }
}
