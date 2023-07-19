package com.wagner.store.service;

import com.wagner.store.dao.entity.Brand;
import com.wagner.store.dao.entity.Item;
import com.wagner.store.dao.entity.Product;
import com.wagner.store.dao.repo.BrandRepository;
import com.wagner.store.dao.repo.ItemRepository;
import com.wagner.store.dao.repo.ProductRepository;
import com.wagner.store.dto.ItemDTO;
import com.wagner.store.errorHandling.StoreException;
import com.wagner.store.util.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.wagner.store.util.MapperUtil.toItemDTO;

@Service
@AllArgsConstructor
@Slf4j
public class ItemsService {
    private ItemRepository itemRepository;
    private ProductRepository productRepository;
    private BrandRepository brandRepository;

    public List<ItemDTO> getAllItems() {
        log.info("Retrieve all items");
        return itemRepository.findAll()
                .stream()
                .map(MapperUtil::toItemDTO)
                .collect(Collectors.toList());
    }

    public List<ItemDTO> getByProductName(String name) {
        log.info("Retrieve items by product name: " + name);
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(Product::getItems)
                .flatMap(List::stream)
                .map(MapperUtil::toItemDTO)
                .collect(Collectors.toList());
    }

    public ItemDTO getById(Long id) throws StoreException {
        log.info("Retrieve product by id: " + id);
        return toItemDTO(itemRepository.findById(id)
                .orElseThrow(() -> new StoreException(String.format("Item with id: %d was not found.", id))));
    }

    public void updatePrice(Long id, Float price) throws StoreException {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new StoreException(String.format("Item with id: %d was not found.", id)));
        item.setPrice(price);
        itemRepository.save(item);
    }

    public void addItem(ItemDTO itemDTO) throws StoreException {
        Product product = productRepository
                .findById(itemDTO.getProductDTO().getId())
                .orElseThrow(() -> new StoreException(String
                        .format("Product with id: %d was not found.", itemDTO.getProductDTO().getId())));

        Brand brand = brandRepository
                .findById(itemDTO.getBrandDTO().getId())
                .orElseThrow(() -> new StoreException(String
                        .format("Product with id: %d was not found.", itemDTO.getBrandDTO().getId())));

        Item item = Item.builder()
                .sku(itemDTO.getSku())
                .price(itemDTO.getPrice())
                .quantity(itemDTO.getQuantity())
                .sold(itemDTO.getSold())
                .available(itemDTO.getAvailable())
                .product(product)
                .brand(brand)
                .build();
        itemRepository.save(item);
    }
}