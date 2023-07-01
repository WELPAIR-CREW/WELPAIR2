package com.hielectro.welpair.product;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.hielectro.welpair.configuration.MybatisConfiguration;
import com.hielectro.welpair.configuration.WelpairApplication;
import com.hielectro.welpair.sellproduct.model.service.SellProductServiceImpl;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@ContextConfiguration(classes = {MybatisConfiguration.class, WelpairApplication.class})
public class ProductServiceTests {
    @Autowired
    SellProductServiceImpl productService;

    @Test
    @DisplayName("Service 의존성 주입 확인")
    public void test() {
        assertNotNull(productService);
    }

    @Test
    @DisplayName("리턴값의 Null 여부 확인")
    public void test2() {
        Map<String, String> map = new HashMap<>();
        assertNotNull(productService.selectProductList(map));
    }

    @Test
    @DisplayName("리뷰 데이터를 불러오는지 여부 확인")
    public void test3() {
        Map<String, Object> searchMap = new HashMap<>();
        assertNotNull(productService.selectReviewList(searchMap));
        System.out.println(productService.selectReviewList(searchMap));
    }
}
