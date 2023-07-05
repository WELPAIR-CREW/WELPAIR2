package com.hielectro.welpair.payment.controller;

import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.payment.model.service.PayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
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

    @PostMapping("pay")
    @ResponseBody
    public String gotopay(@RequestBody ArrayList<CartGeneralDTO> cartSellProduct){

        System.out.println("payment postmapping 들어옴");
        System.out.println(cartSellProduct);

        // 타임리프로 payment/pay페이지에 데이터 뿌려야하는데..... getmapping으로 넘어가면 안돼여......

        return "success";
    }





}
