package com.abn.amro.assignment.model.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IngredientDTO {
    private Long id;
    @NotBlank
    private String title;
}
