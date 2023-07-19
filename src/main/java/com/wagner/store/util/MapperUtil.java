package com.wagner.store.util;

import com.wagner.store.dao.entity.Brand;
import com.wagner.store.dao.entity.Category;
import com.wagner.store.dao.entity.Item;
import com.wagner.store.dao.entity.Product;
import com.wagner.store.dto.BrandDTO;
import com.wagner.store.dto.CategoryDTO;
import com.wagner.store.dto.ItemDTO;
import com.wagner.store.dto.ProductDTO;

public class MapperUtil {

    public static CategoryDTO toCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public static ItemDTO toItemDTO(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .sku(item.getSku())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .available(item.getAvailable())
                .sold(item.getSold())
                .brandDTO(toBrandDTO(item.getBrand()))
                .productDTO(toProductDTO(item.getProduct()))
                .build();
    }

    public static BrandDTO toBrandDTO(Brand brand) {
        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .build();
    }

    public static ProductDTO toProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(toCategoryDTO(product.getCategory()))
                .build();
    }
}