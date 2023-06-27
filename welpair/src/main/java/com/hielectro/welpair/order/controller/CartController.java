package com.hielectro.welpair.order.controller;


import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping({"/cart"})
public class CartController {

    private final OrderService orderService;

    private CartController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/cart")
    public String cartList(Model model){
        List<CartGeneralDTO> cartList = new ArrayList<>();

        cartList.add(new CartGeneralDTO());











        return "/consumer/order/cart";

    }

//    @GetMapping({"{id}"})
//    public String defaultLocation(@PathVariable("id") String url){
//        return "/consumer/order/" + url;
//    }






}
