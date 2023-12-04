package com.abn.amro.assignment.search;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class RecipeSearchRequest {

    @ArraySchema(
            schema = @Schema(implementation = SearchCriteria.class),
            minItems = 1,
            uniqueItems = true,
            arraySchema = @Schema(description = "List of search criteria")
    )
    @Valid
    private List<SearchCriteria> searchCriteriaList;
    @Schema(description = "Search option, is indicate if operator will be AND (all) or OR (any) between criteriaList elements",
            allowableValues = {"any", "all"}, example = "all")

    @NotEmpty(message = "Must be provided")
    private String searchOption;

}
