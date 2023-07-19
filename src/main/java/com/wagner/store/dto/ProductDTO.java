package com.wagner.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    @NotBlank(message = "The name is required.")
    private String name;
    @NotBlank(message = "The description is required.")
    private String description;
    @NotNull(message = "The category is mandatory")
    private CategoryDTO category;
}