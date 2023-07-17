package com.hielectro.welpair.product;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.hielectro.welpair.configuration.MybatisConfiguration;
import com.hielectro.welpair.configuration.WelpairApplication;
import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.sellproduct.model.dao.SellProductMapper;
import com.hielectro.welpair.sellproduct.model.service.SellProductServiceImpl;

@SpringBootTest
@ContextConfiguration(classes = {MybatisConfiguration.class, WelpairApplication.class})
public class ProductServiceTests {
    @Autowired
    SellProductServiceImpl productService;

    @Autowired
    SellProductMapper productMapper;

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

    @Test
    @DisplayName("옵션리스트 출력 확인")
    public void selectOptionList() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryCode("3");
        assertNotNull(productMapper.selectOptionList(productDTO));
        System.out.println(productMapper.selectOptionList(productDTO));
    }
}
