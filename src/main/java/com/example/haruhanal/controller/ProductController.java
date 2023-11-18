package com.example.haruhanal.controller;

import com.example.haruhanal.dto.ProductDTO;
import com.example.haruhanal.entity.Product;
import com.example.haruhanal.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private final ProductService productService;

    /**
     * 검색 및 페이징 1
     */
    @GetMapping("/search")
    public Page<ProductDTO> searchProduct(@RequestParam("keyword") String keyword, @RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> pages = productService.searchProduct(keyword, pageable);
        return pages.map(ProductDTO::new);
    }

    /**
     * 특정 제품 정보 가져오기
     */
    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("product_id") Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (product.isPresent()) {
            ProductDTO productDTO = new ProductDTO(product.get());
            return ResponseEntity.ok(productDTO);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 제품 생성
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = Product.builder()
                .title(productDTO.getTitle())
                .text(productDTO.getText())
                .product_image_url(productDTO.getProduct_image_url())
                .build();
        Long savedProductId = productService.saveProduct(product);
        return ResponseEntity.ok(savedProductId);
    }

    /**
     * 제품 정보 업데이트
     */
    @PutMapping("/{product_id}")
    public ResponseEntity<Long> updateProduct(@PathVariable("product_id") Long id, @RequestBody ProductDTO productDTO) {
        Optional<Product> savedProduct = productService.getProduct(id);
        if (savedProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Product product = Product.builder()
                .title(productDTO.getTitle())
                .text(productDTO.getText())
                .product_image_url(productDTO.getProduct_image_url())
                .build();
        productService.updateProduct(id, product);
        return ResponseEntity.ok(id);
    }

    /**
     * 제품 삭제
     */
    @DeleteMapping("/{product_id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable("product_id") Long id) {
        Optional<Product> savedProduct = productService.getProduct(id);
        if (savedProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Long deletedProductId = productService.deleteProduct(id);
        return ResponseEntity.ok(deletedProductId);
    }
}
