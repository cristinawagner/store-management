package com.wagner.store.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long id;
    private String sku;
    @NotNull
    @Positive
    private Float price;
    private Integer quantity;
    private Integer sold;
    private Integer available;
    private ProductDTO productDTO;
    private BrandDTO brandDTO;
}