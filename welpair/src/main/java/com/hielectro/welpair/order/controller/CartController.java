
package com.hielectro.welpair.order.controller;

import com.hielectro.welpair.common.PriceCalculator;
import com.hielectro.welpair.order.model.dto.CartDTO;
import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.order.model.service.CartService;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.ThumbnailImageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// 고치기
@Slf4j
@Controller
@PreAuthorize("hasRole('MEMBER')")
@RequestMapping({"/order"})
public class CartController {

    private final CartService cartService;

    private CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 카트에 상품담기(임시)
    @GetMapping("/cart/add")
    public String addCart() {
        return "consumer/order/add";
    }


    // 장바구니 상품 담기 인서트용
    @ResponseBody
    @PostMapping(value = "/cart/add", produces = "application/json; charset=utf-8")
    public Map<String, String> addCart(@ModelAttribute CartSellProductDTO cartSellProduct,
                                       @AuthenticationPrincipal User user
    ) {
        // 카트별판매상품dto를 통해 매상품id와 수량 정보와, 회원정보ID가 넘어온다.
        System.out.println("선택상품 : " + cartSellProduct);

        // 결과 메세지 전달 map 객체
        Map<String, String> resultMap = new HashMap<>();

        // 판매상품 조회 메소드 결과객체
        SellProductDTO sellProduct =
                cartService.isSellProductById(cartSellProduct.getSellProductId());

        // 1. 정상 수량인지 체크(프론트에서도 검증)
        if (cartSellProduct.getCartAmount() < 1) {
            resultMap.put("message", "수량이 잘못되었습니다.");
            return resultMap;
        }
        // 2. 판매상품 ID를 통해 현재 판매중인 상품인지 조회
        else if (sellProduct.getIsSell().equals('N')) {
            resultMap.put("message", "판매중인 상품이 아닙니다.");
            return resultMap;
        }
        // 3. 정상 데이터로 검증 통과
        else {

            // 회원정보를 조회하여 카트가 생성되어있으면 카트번호를 조회해온다. 없는 경우 생성한다.
            CartDTO cart = cartService.checkoutCartByMemberId(user.getUsername());
            log.info("cart");

            // 장바구니 미생성 회원
            if (cart == null) {
                // 장바구니 테이블을 생성한다.
                cartService.makeCart(user.getUsername());
                // 다시 장바구니 정보 조회
                cart = cartService.checkoutCartByMemberId(user.getUsername());
                // 장바구니(카트) 넘버를 세팅한다.
                cartSellProduct.setCartNo(cart.getCartNo());
            }

            cartSellProduct.setCartNo(cart.getCartNo());
            System.out.println("cartSell 정보 : " + cartSellProduct);

            // 장바구니에 같은 상품을 담은 경우
            int checkPrd = cartService.checkoutCartProductById(cartSellProduct);
            if (checkPrd > 0) {
                resultMap.put("message", "이미 장바구니에 존재하는 상품입니다.");
                return resultMap;
            }

            // cartSellProduct 테이블 데이터 삽입하러 가기
            int result = cartService.addCartSellProduct(cartSellProduct);
            if (result > 0) {
                //장바구니 담기 성공시
                System.out.println("장바구니 담기 성공1111");
                resultMap.put("message", "장바구니 담기에 성공하였습니다.");

                return resultMap;

            } else {   // 실패
                log.info("log 확인");
                System.out.println("장바구니 담기 실패2222");
                resultMap.put("message", "장바구니 담기에 실패하였습니다. 다시 시도해주세요.");
                return resultMap;
            }
        }
    }

    // 회원 장바구니 불러오기
    @GetMapping("cart")
    public String cartList(Model model
            , @AuthenticationPrincipal User user
    ) {


        System.out.println("User =======================" + user);


        // 미로그인 사용자 null 처리
        if(user == null){
            return "consumer/order/cart-blank";

        }

        // 1. 회원 정보 받아서 해당 회원의 장바구니 조회

        // 2. 장바구니 관련 테이블 리스트로 받아옴
        List<CartGeneralDTO> cartList = cartService.cartAllInfoSelect(user.getUsername());

        List<ThumbnailImageDTO> thumbnail = new ArrayList<>();

        System.out.println(cartList.size());
        for (CartGeneralDTO cart : cartList) {
            try {
                priceMaker(cart);
                System.out.println(cart.getSellPage());
                model.addAttribute("expt", cartList.get(cartList.size()-1));

            } catch (NullPointerException e) {

                return "consumer/order/cart-blank";
            }
        }
        // 3. 장바구니 상품정보 모델에 담아 뷰로 전달
        model.addAttribute("cartList", cartList);
//        model.addAttribute("thumbnail", thumbnail);

        return "consumer/order/cart";

    }

    // 수량변경
    @ResponseBody
    @PostMapping("/cart/amount-change")
    public String cartAmountChange(@ModelAttribute CartSellProductDTO cartSellProduct, Model model,
                                   @AuthenticationPrincipal User user
    ) {

        String response = "";

        System.out.println("수량변경 컨트롤러");
        System.out.println(cartSellProduct);

        cartSellProduct.setCart(new CartDTO());
        cartSellProduct.getCart().setEmpNo(user.getUsername());
        boolean result = cartService.cartAmountChange(cartSellProduct);

        System.out.println(result);
        if(result){
            response = "수량변경 성공";
        }
        else {
            response = "수량변경 실패";
        }

        return response;
    }

    // 선택상품 삭제
    @ResponseBody
    @PostMapping(value = "cart/delete")
    public String deleteCart(Model model, @RequestBody ArrayList<String> productList, @AuthenticationPrincipal User user
    ){
        String response = "";

        System.out.println("컨트롤러 들어옴 cart/delete");
        System.out.println(productList);

        boolean result = cartService.deleteCartProduct(productList, user.getUsername());

        if(result){
            response = "선택상품 삭제 성공";
        }
        else {
            response = "선택상품 삭제 실패";
        }
        return response;
    }


    // 선택상품에 따른 예상결제 가격변동
    @ResponseBody
    @PostMapping("cart/exptprice")
    public Model selectCart(Model model, @RequestBody ArrayList<ArrayList<String>> exptPriceList){

        System.out.println("cart/exptPrice 컨트롤러");
        System.out.println(exptPriceList);

        System.out.println(exptPriceList);

        model.addAttribute("expt", exptPriceMaker(exptPriceList));

        return model;
    }


    // 단품 금액 생성 메소드
    public static void priceMaker(CartGeneralDTO cart) {

        // 1품목 가격 (가격 * 수량)
        cart.setPrice(new PriceCalculator().amountOfPrice(cart.getProduct().getProductPrice(), cart.getSellProduct().getDiscount(), cart.getCartSellProduct().getCartAmount()));

        // 1품목 총합계 (1품목 가격 + 배송비)
        cart.setTotalPrice(cart.getPrice() + cart.getCartSellProduct().getDeliveryPrice());

    }

    // 예상 총 결제금액 생성메소드(체크박스 선택시 바뀐다)
    public CartGeneralDTO exptPriceMaker(ArrayList<ArrayList<String>> exptPriceList) {
        // 선택 총 합계

        int exptPrice = 0;
        int exptDeliveryPrice = 0;
        int exptTotalPrice = 0;

        for(ArrayList<String> expt : exptPriceList){

            exptPrice += Integer.parseInt(expt.get(0));
            exptDeliveryPrice += Integer.parseInt(expt.get(1));
            exptTotalPrice += Integer.parseInt(expt.get(2));

        }

        CartGeneralDTO expt = new CartGeneralDTO();
        expt.setExptPrice(exptPrice);
        expt.setExptDeliveryPrice(exptDeliveryPrice);
        expt.setExptTotalPrice(exptTotalPrice);

        return expt;



    }


}
