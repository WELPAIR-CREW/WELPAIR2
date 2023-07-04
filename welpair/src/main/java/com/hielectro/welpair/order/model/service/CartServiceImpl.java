package com.hielectro.welpair.order.model.service;

import com.hielectro.welpair.order.model.dao.CartMapper;
import com.hielectro.welpair.order.model.dto.CartDTO;
import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {


    private final CartMapper cartMapper;

    public CartServiceImpl(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }


    @Transactional
    @Override
    public int makeCart(String empNo) {
        return cartMapper.makeCart(empNo);
    }

    @Override
    public String selectCartNo() {
        return cartMapper.selectCartNo();
    }

    @Transactional
    @Override
    public int addCartSellProduct(CartSellProductDTO cartSellProduct) {
        return cartMapper.addCartSellProduct(cartSellProduct);
    }

    //
    @Override
    public CartDTO checkoutCartByMemberId(String empNo) {
        return cartMapper.checkoutCartByMemberId(empNo);
    }

    @Override
    public SellProductDTO isSellProductById(String sellProductId) {
        return cartMapper.isSellProductById(sellProductId);
    }


    @Override
    public int checkoutCartProductById(CartSellProductDTO cartSellProduct) {
        return cartMapper.checkoutCartProductById(cartSellProduct);
    }

    @Override
    public List<CartGeneralDTO> cartAllInfoSelect(String empNo) {
        return cartMapper.cartAllInfoSelect(empNo);
    }

    @Transactional
    @Override
    public boolean cartAmountChange(CartSellProductDTO cartSellProduct) {
        return cartMapper.cartAmountChange(cartSellProduct) > 0 ? true : false;
    }

    @Transactional
    @Override
    public boolean deleteCartProduct(ArrayList<String> productList, String empNo) {

        int result = 0;

        for(String product : productList){
            result += cartMapper.deleteCartProduct(product, empNo);
        }
        return result < productList.size() ? true : false;
    }

}
