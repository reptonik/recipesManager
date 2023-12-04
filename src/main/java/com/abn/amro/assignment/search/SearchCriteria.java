package com.abn.amro.assignment.search;

import com.abn.amro.assignment.model.domain.RecipeDTO;
import com.abn.amro.assignment.search.validation.ValidFilterKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class SearchCriteria {
    @Schema(description = "Key to filter on. Any field name from recipe", example = "title")
    @ValidFilterKey(entity = RecipeDTO.class, message = "filterKey value is invalid")
    private String filterKey;
    @Schema(description = "Value to search", example = "pasta")
    private Object value;
    @Schema(description = "Key to filter on. Any field name from recipe", example = "cn",
    allowableValues = {"cn", "nc", "eq", "ne"})
    @ValidFilterKey(predefinedKeys = {"cn", "nc", "eq", "ne"}, message = "Unknown operation value")
    private String operation;
    @Schema(description = "Is it ingredient", example = "true")
    private boolean isIngredient;
}
