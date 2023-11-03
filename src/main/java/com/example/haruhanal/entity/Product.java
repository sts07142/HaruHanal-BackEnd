package com.example.haruhanal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String text;
    private String product_image_url;
    @JsonIgnore
    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE)
    private List<Category_item> category_items = new ArrayList<>();


}
