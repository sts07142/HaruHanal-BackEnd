package com.example.haruhanal.controller;

import com.example.haruhanal.dto.ProductDTO;
import com.example.haruhanal.entity.Product;
import com.example.haruhanal.entity.User;
import com.example.haruhanal.enums.Gender;
import com.example.haruhanal.enums.UserRole;
import com.example.haruhanal.service.ProductService;
import com.example.haruhanal.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testSearchProduct() throws Exception {
        String name = "vitamin";
        Pageable pageable = PageRequest.of(0, 6);
        List<Product> products = new ArrayList<Product>();

        for (int i = 0; i < 30; i++) {
            products.add(Product.builder()
                    .title("vitamin" + i)
                    .text("Good!" + i)
                    .product_image_url("abc.com" + i)
                    .build());
        }

        List<Product> pageContent = products.subList(0, 6);
        Page<Product> mockPage = new PageImpl<>(pageContent, pageable, products.size());
        when(productService.searchProduct(name, pageable)).thenReturn(mockPage);

        mockMvc.perform(get("/v1/products/search?keyword={keyword}&page={page}&size={size}", "vitamin",0 , 6))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("vitamin0"))
                .andExpect(jsonPath("$.content[0].text").value("Good!0"))
                .andExpect(jsonPath("$.content[0].product_image_url").value("abc.com0"));

    }
    
    
    @Test
    void testGetProduct() throws Exception {
        Product mockProduct = Product.builder()
                .id(1L)
                .title("Test Title")
                .text("Test Text")
                .product_image_url("123.com")
                .build();

        when(productService.getProduct(1L)).thenReturn(Optional.of(mockProduct));

        mockMvc.perform(get("/v1/products/{product_id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.text").value("Test Text"))
                .andExpect(jsonPath("$.product_image_url").value("123.com"));
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductDTO productDTO = ProductDTO.builder()
                .title("Test Title")
                .text("Test Text")
                .product_image_url("123.com")
                .build();

        mockMvc.perform(post("/v1/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductDTO updatedProductDTO = ProductDTO.builder()
                .id(1L)
                .title("Updated Title")
                .text("Updated Text")
                .product_image_url("456.com")
                .build();

        mockMvc.perform(put("/v1/products/{product_id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedProductDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Long mockProductId = 1L;

        when(productService.deleteProduct(mockProductId)).thenReturn(mockProductId);

        mockMvc.perform(delete("/v1/products/{product_id}", mockProductId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}