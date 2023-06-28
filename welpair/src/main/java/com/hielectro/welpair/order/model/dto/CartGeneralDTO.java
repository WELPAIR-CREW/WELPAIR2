package com.hielectro.welpair.order.model.dto;


import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellItemPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartGeneralDTO {

    private final CartDTO cart = new CartDTO();
    private final CartSellProductDTO cartSellProduct = new CartSellProductDTO();
    private final SellProductDTO sellProduct = new SellProductDTO();
    private final SellItemPageDTO sellItemPage = new SellItemPageDTO();
    private final SellPageDTO sellPage = new SellPageDTO();
    private final SellImageDTO sellImage = new SellImageDTO();
    private final ProductDTO product = new ProductDTO();

    // 카트상품 단품 1개 가격 = 원가 * 할인율
    private int price = (int) (product.getProductPrice() * (1-sellProduct.getDiscount()));
    // 카트상품 단품별 총수량 가격합계
    private int totalPrice = price * cartSellProduct.getCartAmount() + cartSellProduct.getDeliveryPrice();

}

