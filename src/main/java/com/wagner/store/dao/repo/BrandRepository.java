package com.wagner.store.dao.repo;

import com.wagner.store.dao.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}