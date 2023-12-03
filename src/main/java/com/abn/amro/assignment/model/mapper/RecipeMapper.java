package com.abn.amro.assignment.model.mapper;


import com.abn.amro.assignment.model.domain.RecipeDTO;
import com.abn.amro.assignment.model.entity.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecipeMapper {

    RecipeMapper MAPPER = Mappers.getMapper(RecipeMapper.class);

    RecipeDTO mapToRecipeDto(Recipe recipe);
    Recipe mapToRecipeEntity(RecipeDTO recipeDTO);
}
