package com.wagner.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {

    private Long id;
    @NotBlank(message = "The name is required.")
    private String name;
    @NotBlank(message = "The description is required.")
    private String description;
}