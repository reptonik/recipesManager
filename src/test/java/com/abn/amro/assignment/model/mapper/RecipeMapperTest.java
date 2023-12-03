package com.abn.amro.assignment.model.mapper;

import com.abn.amro.assignment.model.domain.RecipeDTO;
import com.abn.amro.assignment.model.entity.Ingredient;
import com.abn.amro.assignment.model.entity.Recipe;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeMapperTest {

    @Test
    void mapToRecipeDto() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setTitle("Test1");
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setRecipeIngredients(Set.of(ingredient));
        recipe.setInstructions("This is test instructions");
        recipe.setServings(2);
        recipe.setIsVegetarian(true);

        RecipeDTO recipeDTO = RecipeMapper.MAPPER.mapToRecipeDto(recipe);

        assertEquals(recipe.getId(), recipeDTO.getId());
        assertEquals(recipe.getInstructions(), recipeDTO.getInstructions());
        assertEquals(recipe.getServings(), recipeDTO.getServings());
        assertEquals(recipe.getIsVegetarian(), recipeDTO.getIsVegetarian());
        assertTrue(recipe.getRecipeIngredients().containsAll(recipeDTO.getRecipeIngredients()));
    }

    @Test
    void mapToRecipeEntity() {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(1L);
        recipeDTO.setInstructions("This is test instructions");
        recipeDTO.setServings(2);
        recipeDTO.setIsVegetarian(true);

        Recipe recipe = RecipeMapper.MAPPER.mapToRecipeEntity(recipeDTO);

        assertEquals(recipeDTO.getId(), recipe.getId());
        assertEquals(recipeDTO.getInstructions(), recipe.getInstructions());
        assertEquals(recipeDTO.getServings(), recipe.getServings());
        assertEquals(recipeDTO.getIsVegetarian(), recipe.getIsVegetarian());
        assertTrue(recipeDTO.getRecipeIngredients().containsAll(recipe.getRecipeIngredients()));
    }
}