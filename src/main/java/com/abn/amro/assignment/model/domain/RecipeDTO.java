package com.abn.amro.assignment.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RecipeDTO {
    @Schema(description = "Recipe ID", example = "1")
    private Long id;
    @Schema(description = "Recipe title", example = "Vegetarian Pasta")
    @NotBlank
    @Size(min = 4, max = 50)
    private String title;
    @Schema(description = "Number of servings", example = "4")
    @NotBlank
    private Integer servings;
    @Schema(description = "Recipe instructions", example = "Boil pasta and add vegetables.")
    @NotBlank
    @Size(min = 5, max = 255)
    private String instructions;
    @Schema(description = "Flag indicating whether the recipe is vegetarian", example = "true", implementation = Boolean.class)
    private Boolean isVegetarian;
    @ArraySchema(
            schema = @Schema(implementation = IngredientDTO.class),
            minItems = 1,
            uniqueItems = true,
            arraySchema = @Schema(description = "Ingredients in the recipe")
    )
    private Set<IngredientDTO> recipeIngredients = new HashSet<>();


    @JsonIgnore
    public boolean hasId() {
        return this.id != null && this.id > 0;
    }
}
