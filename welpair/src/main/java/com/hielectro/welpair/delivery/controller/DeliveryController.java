package com.hielectro.welpair.delivery.controller;

import com.hielectro.welpair.common.Pagination;
import com.hielectro.welpair.delivery.model.dto.DeliveryDTO;
import com.hielectro.welpair.delivery.model.dto.DriverDTO;
import com.hielectro.welpair.delivery.model.dto.NotDeliveryDTO;
import com.hielectro.welpair.delivery.model.dto.OrderProductDTO;
import com.hielectro.welpair.delivery.model.service.deliveryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    private final deliveryService service;

    public DeliveryController(deliveryService service) {
        this.service = service;
    }

    /* 배송 전체 현황 */
    @GetMapping("delivery_main")
    public String deliveryList(Model model) {
//      service의 메소드호출. 반환값 결과값에 따라 보여주는 View를 다르게 한다.
        List<OrderProductDTO> deliverylist = service.deliveryDelivery();
        System.out.println(deliverylist);
        model.addAttribute("deliveryList", deliverylist);
        return "admin/delivery/delivery_main";
    }

    /* 상품준비중 */
//    @GetMapping("delivery_prepare")
//
//    public String deliveryprepare(Model model) {
//        List<OrderProductDTO> deliverylist = service.deliveryDelivery();
//        System.out.println(deliverylist);
//        model.addAttribute("deliveryList", deliverylist);
//        return "admin/delivery/delivery_prepare";
//
//    }

    /* 상품준비중 2*/
    @GetMapping("delivery_prepare")

    public String notdelivery(Model model,
                              @RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {

        Map<String, Object> pagingmap = new HashMap<>();
        pagingmap.put("pageNo", pageNo);

        int result = service.notDeliveryCount();
        System.out.println("result = " + result);
        Pagination.init(null);
        Map<String, Integer> paging = Pagination.paging(result, pageNo);
        System.out.println("paging = " + paging);

        model.addAttribute("paging", paging);

        List<NotDeliveryDTO> notDeliverylist = service.notDelivery(pagingmap);
        System.out.println(notDeliverylist);
        model.addAttribute("notDeliveryList", notDeliverylist);

        return "admin/delivery/delivery_prepare";

    }

    /* 배송중 */
    @GetMapping("delivery_transit")
    public String deliverytransit(Model model,
                                  @RequestParam(value = "pageNo", defaultValue = "1") int pageNo){

        Map<String, Object> pagingmap = new HashMap<>();
        pagingmap.put("pageNo", pageNo);

        int result = service.notDeliveryCount();
        System.out.println("result = " + result);
        Pagination.init(null);
        Map<String, Integer> paging = Pagination.paging(result, pageNo);
        System.out.println("paging = " + paging);

        model.addAttribute("paging", paging);

        List<OrderProductDTO> deliverylist = service.deliveryDelivery();
        System.out.println(deliverylist);
        model.addAttribute("deliveryList", deliverylist);
        return "admin/delivery/delivery_transit";

    }

    /* 배송완료 */
    @GetMapping("delivery_complete")
    public String deliverycomplete(Model model) {
        List<OrderProductDTO> deliverylist = service.deliveryDelivery();
        System.out.println(deliverylist);
        model.addAttribute("deliveryList", deliverylist);
        return "admin/delivery/delivery_complete";
    }

}
