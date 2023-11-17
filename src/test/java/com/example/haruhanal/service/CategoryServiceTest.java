package com.example.haruhanal.service;

import com.example.haruhanal.entity.Category;
import com.example.haruhanal.repository.CategoryRepository;
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
class CategoryServiceTest {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;

    @Test
    public void 카테고리생성() throws Exception {
        // given
        Category category = Category.builder()
                .name("비타민")
                .build();
        // when
        Long savedId = categoryService.saveCategory(category);
        // then
        assertEquals(category, categoryService.getCategory(savedId).get());
    }

    @Test
    public void 카테고리_삭제() throws Exception {
        // given
        Category category = Category.builder()
                .name("비타민")
                .build();
        // when
        Long savedId = categoryService.saveCategory(category);
        categoryService.deleteCategory(savedId);
        // then
        if (!categoryService.getCategory(savedId).isEmpty()) {
            fail("삭제 실패");
        }
    }
}