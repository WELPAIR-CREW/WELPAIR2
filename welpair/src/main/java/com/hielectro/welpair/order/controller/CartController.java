package com.hielectro.welpair.order.controller;


import com.hielectro.welpair.order.model.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/cart"})
public class CartController {

    private final OrderService orderService;

    private CartController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping({"id"})
    public String defaultLocation(@PathVariable("id") String url){
        return "/consumer/cart/" + url;
    }







}
