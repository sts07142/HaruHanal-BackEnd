package com.example.haruhanal.controller;

import com.example.haruhanal.entity.Category;
import com.example.haruhanal.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Category API")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * 카테고리 생성
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createCategory(@RequestBody Category category) {
        Long savedCategoryId = categoryService.saveCategory(category);
        return ResponseEntity.ok(savedCategoryId);
    }

    /**
     * 카테고리 삭제
     */
    @DeleteMapping("/{category_id}")
    public ResponseEntity<Long> deleteCategory(@PathVariable("category_id") Long id) {
        Optional<Category> savedCategory = categoryService.getCategory(id);
        if (savedCategory.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Long deletedCategoryId = categoryService.deleteCategory(id);
        return ResponseEntity.ok(deletedCategoryId);
    }
}
