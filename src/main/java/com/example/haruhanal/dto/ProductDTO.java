package com.example.haruhanal.dto;

import com.example.haruhanal.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String title;
    private String text;
    private String product_image_url;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.text = product.getText();
        this.product_image_url = product.getProduct_image_url();
    }
    @Builder
    public ProductDTO(Long id, String title, String text, String product_image_url) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.product_image_url = product_image_url;
    }

}
