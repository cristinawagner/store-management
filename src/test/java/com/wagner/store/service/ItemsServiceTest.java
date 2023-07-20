package com.wagner.store.service;

import com.wagner.store.dao.entity.Brand;
import com.wagner.store.dao.entity.Category;
import com.wagner.store.dao.entity.Item;
import com.wagner.store.dao.entity.Product;
import com.wagner.store.dao.repo.BrandRepository;
import com.wagner.store.dao.repo.ItemRepository;
import com.wagner.store.dao.repo.ProductRepository;
import com.wagner.store.dto.BrandDTO;
import com.wagner.store.dto.ItemDTO;
import com.wagner.store.dto.ProductDTO;
import com.wagner.store.errorHandling.StoreException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.wagner.store.util.MapperUtil.toItemDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemsServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private BrandRepository brandRepository;
    @InjectMocks
    private ItemsService itemsService;

    @Test
    void findAllShouldReturnItemsList() {
        // Given
        Item item = getItem();

        // When
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<ItemDTO> actual = itemsService.getAllItems();

        // Then
        List<ItemDTO> expected = List.of(toItemDTO(item));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getSku(), actual.get(0).getSku());
        verify(itemRepository).findAll();
    }

    @Test
    void findAllShouldReturnEmptyList() {
        // Given

        // When
        when(itemRepository.findAll()).thenReturn(Collections.emptyList());

        List<ItemDTO> actual = itemsService.getAllItems();

        // Then
        assertEquals(0, actual.size());
        verify(itemRepository).findAll();
    }

    @Test
    void getByProductNameShouldReturnItemsList() throws StoreException {
        // Given
        Product product = getProduct();
        Item item = getItem();

        // When
        when(productRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(List.of(product));
        when(itemRepository.findByProductIn(anyList())).thenReturn(List.of(item));

        List<ItemDTO> actual = itemsService.getByProductName("test product name");

        // Then
        assertEquals(1, actual.size());
        assertEquals(product.getItems().get(0).getId(), actual.get(0).getId());
        verify(productRepository).findByNameContainingIgnoreCase(anyString());
        verify(itemRepository).findByProductIn(anyList());
    }

    @Test
    void getByProductNameShouldThrowException() {
        // Given

        // When
        when(productRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(Collections.emptyList());

        assertThrows(StoreException.class, () -> itemsService.getByProductName("name1"));

        // Then
        verify(productRepository).findByNameContainingIgnoreCase(anyString());
        verify(itemRepository, never()).findByProductIn(anyList());
    }

    @Test
    void getByIdShouldReturnItem() throws StoreException {
        // Given
        Item expected = getItem();

        // When
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        ItemDTO actual = itemsService.getById(1L);

        // Then
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getSku(), actual.getSku());
        verify(itemRepository).findById(anyLong());
    }

    @Test
    void getByIdShouldThrowException() {
        // Given

        // When
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(StoreException.class, () -> itemsService.getById(1L));

        // Then
        verify(itemRepository).findById(anyLong());
    }

    @Test
    void updatePriceShouldBeSuccessful() throws StoreException {
        // Given
        Item expected = getItem();
        Float newPrice = 167F;

        // When
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        ItemDTO response = itemsService.updatePrice(1L, newPrice);

        // Then
        assertEquals(expected.getId(), response.getId());
        assertEquals(expected.getSku(), response.getSku());
        assertEquals(newPrice, response.getPrice());
        verify(itemRepository).findById(anyLong());
        verify(itemRepository).save(any());
    }

    @Test
    void updatePriceShouldThrowException() {
        // Given
        // When calling the mocked repository method
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(StoreException.class, () -> itemsService.updatePrice(1L, 123F));
        // Then
        verify(itemRepository).findById(anyLong());
        verify(itemRepository, never()).save(any());
    }

    @Test
    void addItemShouldBeSuccessful() throws StoreException {
        // Given
        Product product = getProduct();
        Brand brand = getBrand();

        // When
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brand));

        itemsService.addItem(getItemDTO());

        // Then
        verify(productRepository).findById(anyLong());
        verify(brandRepository).findById(anyLong());
        verify(itemRepository).save(any());
    }

    @Test
    void addItemShouldThrowExceptionForProductNotFound() {
        // Given
        // When calling the mocked repository method
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(StoreException.class, () -> itemsService.addItem(getItemDTO()));
        // Then
        verify(productRepository).findById(anyLong());
        verify(brandRepository, never()).findById(anyLong());
        verify(itemRepository, never()).save(any());
    }

    @Test
    void addItemShouldThrowExceptionForBrandNotFound() {
        // Given
        // When calling the mocked repository method
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(getProduct()));
        when(brandRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(StoreException.class, () -> itemsService.addItem(getItemDTO()));
        // Then
        verify(productRepository).findById(anyLong());
        verify(brandRepository).findById(anyLong());
        verify(itemRepository, never()).save(any());
    }

    private static ItemDTO getItemDTO() {
        return ItemDTO.builder()
                .id(1L)
                .sku("XYZ123456")
                .price(100F)
                .quantity(20)
                .available(14)
                .sold(6)
                .brandDTO(BrandDTO.builder().id(1L).build())
                .productDTO(ProductDTO.builder().id(1L).build())
                .build();
    }

    private static Item getItem() {
        return Item.builder()
                .id(1L)
                .sku("XYZ123456")
                .price(100F)
                .quantity(20)
                .available(14)
                .sold(6)
                .brand(getBrand())
                .product(getProduct())
                .build();
    }

    private static Brand getBrand() {
        return Brand.builder()
                .id(1L)
                .name("brand1")
                .description("description brand 1")
                .build();
    }

    private static Product getProduct() {
        return Product.builder()
                .id(1L)
                .name("product1")
                .description("description product 1")
                .category(getCategory())
                .items(List.of(Item.builder()
                        .id(1L)
                        .sku("XYZ123456")
                        .price(100F)
                        .quantity(20)
                        .available(14)
                        .sold(6)
                        .brand(getBrand())
                        .build()))
                .build();
    }

    private static Category getCategory() {
        return Category.builder()
                .id(1L)
                .name("category 1")
                .description("description category 1")
                .build();
    }
}
