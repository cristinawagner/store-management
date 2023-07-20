package com.wagner.store.controller;

import com.wagner.store.dto.ItemDTO;
import com.wagner.store.errorHandling.StoreException;
import com.wagner.store.service.ItemsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/items")
public class ItemsController {

    private ItemsService itemsService;

    @GetMapping("/")
    public List<ItemDTO> getAllItems() {
        return itemsService.getAllItems();
    }

    @GetMapping("/search")
    public List<ItemDTO> getByProductName(@RequestParam("productName") String productName) {
        try {
            return itemsService.getByProductName(productName);
        } catch (StoreException e) {
            log.warn("Product with name: " + productName + " was not found: " + e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/search/{id}")
    public ItemDTO getById(@PathVariable("id") Long id) {
        try {
            return itemsService.getById(id);
        } catch (StoreException e) {
            log.warn("Item with id: " + id + " was not found: " + e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/updatePrice/{id}")
    public ResponseEntity updatePrice(@PathVariable("id") Long id, @RequestBody ItemDTO itemDTO) {
        try {
            if (id != null && itemDTO.getPrice() != null) {
                itemsService.updatePrice(id, itemDTO.getPrice());
            }
        } catch (StoreException e) {
            log.error("Exception while trying to update item price: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity addItem(@RequestBody ItemDTO itemDTO) {
        try {
            itemsService.addItem(itemDTO);
        } catch (StoreException e) {
            log.error("Exception while trying to add new item: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}