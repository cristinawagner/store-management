package com.wagner.store.service;

import com.wagner.store.dao.entity.Category;
import com.wagner.store.dao.entity.Product;
import com.wagner.store.dao.repo.CategoryRepository;
import com.wagner.store.dao.repo.ProductRepository;
import com.wagner.store.dto.ProductDTO;
import com.wagner.store.errorHandling.StoreException;
import com.wagner.store.util.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.wagner.store.util.MapperUtil.toProductDTO;

@Service
@AllArgsConstructor
@Slf4j
public class ProductsService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public List<ProductDTO> getAllProducts() {
        log.info("Retrieve all products");
        return productRepository.findAll()
                .stream()
                .map(MapperUtil::toProductDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getByName(String name) {
        log.info("Retrieve products by name: " + name);
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(MapperUtil::toProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getById(Long id) throws StoreException {
        log.info("Retrieve product by id: " + id);
        return toProductDTO(productRepository.findById(id)
                .orElseThrow(() -> new StoreException(String.format("Product with id: %d was not found.", id))));
    }

    public List<ProductDTO> getByCategoryId(Long categoryId) throws StoreException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new StoreException(String.format("Category with id: %d was not found.", categoryId)));
        log.info("Retrieve products by category: " + category);
        return productRepository.findByCategory(category)
                .stream()
                .map(MapperUtil::toProductDTO)
                .collect(Collectors.toList());
    }

    public void addProduct(ProductDTO productDTO) throws StoreException {
        Category category = categoryRepository
                .findById(productDTO.getCategory().getId())
                .orElseThrow(() -> new StoreException(String
                        .format("Category with id: %d was not found.", productDTO.getCategory().getId())));

        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .category(category)
                .build();
        productRepository.save(product);
    }
}