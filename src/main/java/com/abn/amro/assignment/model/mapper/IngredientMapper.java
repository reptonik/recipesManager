package com.abn.amro.assignment.model.mapper;

import com.abn.amro.assignment.model.domain.IngredientDTO;
import com.abn.amro.assignment.model.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientMapper MAPPER = Mappers.getMapper(IngredientMapper.class);

    IngredientDTO mapToIngredientDto(Ingredient ingredient);
    Ingredient mapToIngredientEntity(IngredientDTO ingredientDTO);
}
