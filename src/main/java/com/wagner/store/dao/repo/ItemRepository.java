package com.wagner.store.dao.repo;

import com.wagner.store.dao.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}