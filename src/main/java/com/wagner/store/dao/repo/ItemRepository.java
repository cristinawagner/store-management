package com.wagner.store.dao.repo;

import com.wagner.store.dao.entity.Item;
import com.wagner.store.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByProductIn(List<Product> products);
}