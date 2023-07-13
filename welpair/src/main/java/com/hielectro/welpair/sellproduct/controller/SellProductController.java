package com.hielectro.welpair.sellproduct.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hielectro.welpair.board.model.dto.BoardDTO;
import com.hielectro.welpair.common.Pagination;
import com.hielectro.welpair.common.Search;
import com.hielectro.welpair.inventory.model.dto.CategoryDTO;
import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDetailDTO;
import com.hielectro.welpair.sellproduct.model.service.SellProductServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/sellproduct")
@Slf4j
public class SellProductController {
    private final SellProductServiceImpl productService;

    private final int limit = 10;
//    @Value("${image.image-dir}")

    public SellProductController(SellProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping({"/","product"})
    @PreAuthorize("hasRole('ADMIN')")
    public String defaultLocation(@AuthenticationPrincipal User user) {
        System.out.println("User +================ " + user);
        return "/admin/sellproduct/product";
    };
    @PostMapping("optionList")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public List<ProductDTO> selectOptionList(@RequestBody ProductDTO product) {
        return productService.selectOptionList(product);
    }

    @PostMapping("categoryList")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public List<CategoryDTO> selectCategoryList() {
        return productService.selectCategoryList();
    }

    @PostMapping("statusList")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public List<ProductDTO> selectProductList() {
        return productService.selectProductStatus();
    }

    @PostMapping("productNameList")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public List<ProductDTO> selectProductName(@RequestBody ProductDTO product) {
        return productService.selectProductNameList(product);
    }

    @GetMapping("review")
    @PreAuthorize("hasRole('ADMIN')")
    public String reviewLocation(HttpServletRequest request, Model model,
                                 @ModelAttribute Search search,
                                 @RequestParam(required = false, defaultValue = "1") int currentPageNo) {
        String queryString = search.toString();
        String url = String.valueOf(request.getRequestURL()) + queryString;
        Map<String, Object> searchMap = new HashMap<>();
        System.out.println("==========================" + search);
        searchMap.put("search", search);
        searchMap.put("pageNo", currentPageNo);
        model.addAttribute("queryString", queryString);

        getPaging(model, currentPageNo, url, () -> productService.reviewSearchCount(searchMap));
        getSelectList(model, () -> productService.selectReviewList(searchMap));
        return "admin/sellproduct/review";
    }

    @PostMapping({"review/private", "QnA/private"})
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    private Map<String, String> setPrivateReview(HttpServletRequest request, HttpServletResponse response, List<BoardDTO> boardList) throws IOException {
        Map<String, String> map = new HashMap<>();
        String requestURI = request.getRequestURI();
        boolean result = productService.setPrivateBoard(boardList);

        if(!result) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Update Failed!");
        }

        if (requestURI.contains("review")) {
            map.put("next_redirect_url", "/sellproduct/review");
        } else {
            map.put("next_redirect_url", "/sellproduct/QnA");
        }

        return map;
    }


    @GetMapping("QnA")
    @PreAuthorize("hasRole('ADMIN')")
    public String qnaLocation(HttpServletRequest request, Model model,
                              @ModelAttribute Search search,
                              @RequestParam(required = false, defaultValue = "1") int currentPageNo) {
        String queryString = search.toString();
        String url = String.valueOf(request.getRequestURL()) + queryString;
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("search", search);
        searchMap.put("pageNo", currentPageNo);
        model.addAttribute("queryString", queryString);

        getPaging(model, currentPageNo, url, () -> productService.qnaSearchCount(searchMap));
        getSelectList(model, () -> productService.selectQnAList(searchMap));
        return "admin/sellproduct/QnA";
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

    private void getSelectList(Model model, Supplier<List<?>> selectListSupplier) {
        List<?> list = selectListSupplier.get();

        model.addAttribute("list", list);
    }

    @PostMapping(value = "sellProductListAPI", produces = "application/json;charset=utf-8")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public List<SellProductDetailDTO> sellProductList(@RequestBody Map<String, String> request) {
        System.out.println("request : " + request);
        List<SellProductDetailDTO> sellProductList = productService.selectProductList(request);
        System.out.println(sellProductList);
        return sellProductList;
    }

    @PostMapping(value = "sellProductCountAPI", produces = "application/json;charset=utf-8")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public Map<String, Integer> sellProductCount(@RequestBody(required = false) Map<String, String> request) {
        int result = productService.sellProductSearchCount(request);
        Map<String, Integer> response = pagination(result);

        return response;
    }

    @PostMapping("updateSellPageByPrivate")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public int updatePrivate(@RequestBody List<String> request) {
        System.out.println(request);
        try {
            return productService.updatePrivate(request);
        } catch (Exception e) {
            throw new IllegalStateException("error");
        }
    }

    @PostMapping("sellProductDeleteAPI")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public int sellProductDelete(@RequestBody List<String> request) {
        System.out.println(request);
        try {
            return productService.sellProductDelete(request);
        } catch (Exception e) {
            throw new IllegalStateException("error");
        }
    }

    public Map<String, Integer> pagination(int length) {
        Map<String, Integer> response = new HashMap<>();
        int maxPageNo = (int) Math.ceil((double) length / limit);

        response.put("maxPageNo", maxPageNo);
        response.put("startPageNo", 1);
        response.put("endPageNo", 5);

        return response;
    }

    @PostMapping("payment")
    @PreAuthorize("hasRole('MEMBER')")
    @ResponseBody
    public List<ProductOrderDTO> test(@ModelAttribute Search search) {
        List<ProductOrderDTO> list = new ArrayList<>();
        list.add(new ProductOrderDTO());
        System.out.println(search);
//        System.out.println(id);
        SellProductDTO sellProduct = productService.selectOneSellProduct(search.getCode());
        list.get(0).setSellProductId(search.getId());
        list.get(0).setProductOrderAmount(search.getAmount());
        list.get(0).setProductOrderPrice((int) (sellProduct.getProduct().getProductPrice() * (1 - sellProduct.getDiscount())));
        list.get(0).setSellproduct(sellProduct);
        return list;
    }
}
