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
import org.springframework.util.CollectionUtils;

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

    public List<ItemDTO> getByProductName(String name) throws StoreException {
        log.info("Retrieve items by product name: " + name);
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        if (CollectionUtils.isEmpty(products)) {
            throw new StoreException(String.format("Product with name: '%s' was not found.", name));
        }

        return itemRepository.findByProductIn(products)
                .stream()
                .map(MapperUtil::toItemDTO)
                .collect(Collectors.toList());
    }

    public ItemDTO getById(Long id) throws StoreException {
        log.info("Retrieve product by id: " + id);
        return toItemDTO(itemRepository.findById(id)
                .orElseThrow(() -> new StoreException(String.format("Item with id: '%d' was not found.", id))));
    }

    public ItemDTO updatePrice(Long id, Float price) throws StoreException {
        log.info("Update Item with id: " + id + " with new price: " + price);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new StoreException(String.format("Item with id: '%d' was not found.", id)));
        item.setPrice(price);
        itemRepository.save(item);
        return toItemDTO(item);
    }

    public void addItem(ItemDTO itemDTO) throws StoreException {
        log.info("Add new item: " + itemDTO.toString());
        Product product = productRepository
                .findById(itemDTO.getProductDTO().getId())
                .orElseThrow(() -> new StoreException(String
                        .format("Product with id: '%d' was not found.", itemDTO.getProductDTO().getId())));

        Brand brand = brandRepository
                .findById(itemDTO.getBrandDTO().getId())
                .orElseThrow(() -> new StoreException(String
                        .format("Product with id: '%d' was not found.", itemDTO.getBrandDTO().getId())));

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