package com.wagner.store.controller;

import com.wagner.store.dto.ProductDTO;
import com.wagner.store.errorHandling.StoreException;
import com.wagner.store.service.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductsController {

    private ProductsService productsService;

    @GetMapping("/")
    public List<ProductDTO> getAllProducts() {
        return productsService.getAllProducts();
    }

    @GetMapping("/search")
    public List<ProductDTO> getByName(@RequestParam("name") String name) {
        return productsService.getByName(name);
    }

    @GetMapping("/search/{id}")
    public ProductDTO getById(@PathVariable("id") Long id) {
        try {
            return productsService.getById(id);
        } catch (StoreException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/search/category/{id}")
    public List<ProductDTO> getByCategoryId(@PathVariable("id") Long id) {
        try {
            return productsService.getByCategoryId(id);
        } catch (StoreException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity addProduct(@RequestBody ProductDTO productDTO) {
        try {
            productsService.addProduct(productDTO);
        } catch (StoreException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}