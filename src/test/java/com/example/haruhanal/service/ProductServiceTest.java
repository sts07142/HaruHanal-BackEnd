package com.example.haruhanal.service;

import com.example.haruhanal.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ProductServiceTest {
    @Autowired ProductService productService;

    @Test
    public void 제품_생성() throws Exception {
        // given
        Product product = Product.builder()
                .title("비타민")
                .text("피로 회복에 좋음")
                .product_image_url("abc.com")
                .build();
        // when
        Long savedId = productService.saveProduct(product);
        // then
        assertEquals(product, productService.getProduct(savedId).get());
    }

    @Test
    public void 제품_업데이트() throws Exception {
        // given
        Product product1 = Product.builder()
                .title("비타민")
                .text("피로 회복에 좋음")
                .product_image_url("abc.com")
                .build();
        Product product2 = Product.builder()
                .title("오메가3")
                .text("피부에 좋음")
                .product_image_url("abc.com")
                .build();
        // when
        Long savedId = productService.saveProduct(product1);
        // then
        try {
            productService.updateProduct(savedId, product2);
        } catch (IllegalStateException e) {
            fail("업데이트 실패");
        }
    }

    @Test
    public void 제품_삭제() throws Exception {
        // given
        Product product1 = Product.builder()
                .title("비타민")
                .text("피로 회복에 좋음")
                .product_image_url("abc.com")
                .build();
        // when
        Long savedId = productService.saveProduct(product1);
        productService.deleteProduct(savedId);
        // then
        if (!productService.getProduct(savedId).isEmpty()) {
            fail("삭제 실패");
        }
    }
}