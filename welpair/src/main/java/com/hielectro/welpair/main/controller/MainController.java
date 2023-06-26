package com.hielectro.welpair.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping({"consumer/header", "admin/header_admin"})
    public void header() {}

    @RequestMapping("admin/footer")
    public void footer() {}
}
