package com.hielectro.welpair.payment.controller;

import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.payment.model.service.PayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping({"/payment"})
public class PayController {

    private final PayService payService;

    private PayController(PayService payService) {
        this.payService = payService;
    }

//    @GetMapping("/pay")
//    public String goPaymentPage(){return "consumer/payment/pay";}

    @PostMapping("/pay")
    public String gotopay(@ModelAttribute ArrayList<CartGeneralDTO> orderInfo){

        System.out.println("payment postmapping 들어옴");
        System.out.println(orderInfo);



        return "consumer/payment/pay";
    }





}
