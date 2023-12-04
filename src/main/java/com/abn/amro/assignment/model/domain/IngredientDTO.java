package com.abn.amro.assignment.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IngredientDTO {
    @Schema(description = "Ingredient ID", example = "1")
    private Long id;
    @Schema(description = "Ingredient title", example = "Salt")
    @NotBlank
    private String title;

    @JsonIgnore
    public boolean hasId() {
        return this.id != null && this.id > 0;
    }
}
