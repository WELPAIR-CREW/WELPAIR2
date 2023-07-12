package com.hielectro.welpair.mypage.controller;

import static com.hielectro.welpair.common.PriceCalculator.empNo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hielectro.welpair.common.Pagination;
import com.hielectro.welpair.common.Search;
import com.hielectro.welpair.mypage.model.dto.OrderListDTO;
import com.hielectro.welpair.mypage.model.service.OrderListService;


@Controller
@RequestMapping("/mypage/myorder")
public class OrderListController {

    private final OrderListService orderListService;

    public OrderListController(OrderListService orderListService) {
        this.orderListService = orderListService;
    }


    // 주문내역 리스트
    @GetMapping("/list")
    public String myOrderList(HttpServletRequest request, Model model,
                              @ModelAttribute Search search,
                              @RequestParam(required = false, defaultValue = "1") int currentPageNo,
                              @RequestParam(required = false) String criteria) {
        String queryString = search.toString();
        String url = String.valueOf(request.getRequestURL()) + queryString;
        Map<String, Object> map = new HashMap<>();
        map.put("pageNo", currentPageNo);
        map.put("empNo", empNo);
        map.put("search", search);
        map.put("criteria", criteria);

        getPaging(model, currentPageNo, url, () -> orderListService.orderListCount(map));
        List<OrderListDTO> list = orderListService.selectOrderList(map);

        list.stream().filter(item -> Optional.<String>ofNullable(item.getDeliveryStatus()).isEmpty())
                .forEach(item -> item.setDeliveryStatus("배송준비중"));

        list.stream().filter(item -> Optional.<String>ofNullable(item.getOrderName()).isEmpty())
                .forEach(item -> item.setOrderName("판매중지상품"));

        model.addAttribute("queryString", queryString);
        model.addAttribute("orderList", list);
        return "consumer/mypage/myorder";

        // 주문일자, 주문번호, 주문금액, // , 상품명 (NULL이면 판매중지 상품), 수량, // 배송상태, 주문상태(일반주문)
        // order, productorder, product //// delivery,(==NULL이면 배송준비중)

    }

    private void getPaging(Model model, int currentPageNo,
                           String url, Supplier<Integer> searchCountSupplier) {
        Map<String, Integer> paging = null;

        if (!Pagination.getURL().equals(url)) {
            Pagination.init(url);
            int result = searchCountSupplier.get();
            paging = Pagination.paging(result, currentPageNo);
        } else {
            paging = Pagination.getParameter(currentPageNo);
        }

        model.addAttribute("paging", paging);

    }
}
