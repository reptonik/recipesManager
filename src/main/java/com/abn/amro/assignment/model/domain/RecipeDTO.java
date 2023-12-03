package com.abn.amro.assignment.model.domain;

import com.abn.amro.assignment.model.entity.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RecipeDTO {
    private Long id;
    @NotBlank
    @Size(min = 4, max = 50)
    private String title;
    @NotBlank
    private Integer servings;
    @NotBlank
    @Size(min = 5, max = 255)
    private String instructions;
    private Boolean isVegetarian;
    private Set<Ingredient> recipeIngredients = new HashSet<>();
}
