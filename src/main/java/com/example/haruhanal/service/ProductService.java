package com.example.haruhanal.service;

import com.example.haruhanal.entity.Product;
import com.example.haruhanal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public Page<Product> searchProduct(String name, Pageable pageable) {
        return productRepository.findByTitleContainingIgnoreCase(name, pageable);
    }
    @Transactional
    public Long saveProduct(Product product) {
        productRepository.save(product);
        return product.getId();
    }

    @Transactional
    public void updateProduct(Long id, Product updatedProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setTitle(updatedProduct.getTitle());
            product.setText(updatedProduct.getText());
            product.setProduct_image_url(updatedProduct.getProduct_image_url());
        } else {
            throw new IllegalStateException("제품 정보 업데이트에 실패하였습니다.");
        }
    }

    @Transactional
    public Long deleteProduct(Long id) {
        productRepository.deleteById(id);
        return id;
    }
}
