package com.hielectro.welpair.product;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.hielectro.welpair.configuration.MybatisConfiguration;
import com.hielectro.welpair.configuration.WelpairApplication;
import com.hielectro.welpair.sellproduct.model.dao.SellProductMapper;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@ContextConfiguration(classes = {MybatisConfiguration.class, WelpairApplication.class})
public class ProductMapperTests {

    @Autowired
    private SellProductMapper productMapper;

    @Test
    @DisplayName("Mapper 의존성 주입 확인")
    public void dependency() {
        assertNotNull(productMapper);
    }

    @Test
    @DisplayName("모든 상품 출력 테스트")
    public void test() {
        assertNotNull(productMapper.findSellProductByPageNo(1));
        System.out.println(productMapper.findSellProductByPageNo(1));
    }

    @Test
    @DisplayName("판매상품 Code 검색 출력 테스트")
    public void test2() {
        assertNotNull(productMapper.findSellProductByCode(new HashMap<String, String>()));
    }
}
