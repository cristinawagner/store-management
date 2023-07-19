package com.wagner.store.controller;

import com.wagner.store.dto.ItemDTO;
import com.wagner.store.errorHandling.StoreException;
import com.wagner.store.service.ItemsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/items")
public class ItemsController {

    private ItemsService itemsService;

    @GetMapping("/")
    public List<ItemDTO> getAllItems() {
        return itemsService.getAllItems();
    }

    @GetMapping("/search")
    public List<ItemDTO> getByProductName(@RequestParam("productName") String productName) {
        return itemsService.getByProductName(productName);
    }

    @GetMapping("/search/{id}")
    public ItemDTO getById(@PathVariable("id") Long id) {
        try {
            return itemsService.getById(id);
        } catch (StoreException e) {
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
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}