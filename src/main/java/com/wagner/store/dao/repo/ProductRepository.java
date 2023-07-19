package com.wagner.store.dao.repo;

import com.wagner.store.dao.entity.Category;
import com.wagner.store.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategory(Category category);
}