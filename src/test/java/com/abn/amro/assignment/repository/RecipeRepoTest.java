package com.abn.amro.assignment.repository;

import com.abn.amro.assignment.model.entity.Ingredient;
import com.abn.amro.assignment.model.entity.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RecipeRepoTest {

    @Autowired
    private RecipeRepo recipeRepo;

    @Test
    void saveRecipe_success_entityRecipeIsPersisted() {
        // Given
        Recipe recipeToSave = cteateTestRecipe();

        // Action
        Recipe savedRecipe = recipeRepo.save(recipeToSave);
        Recipe foundRecipe = recipeRepo.findById(savedRecipe.getId()).orElse(null);

        // Testing
        assertNotNull(foundRecipe.getId());
        assertNotNull(foundRecipe);
        assertEquals("Test Recipe", foundRecipe.getTitle());
        assertEquals(4, foundRecipe.getServings());
        assertEquals("Test instructions", foundRecipe.getInstructions());
        assertTrue(foundRecipe.getIsVegetarian());
    }

    @Test
    void save_WithIngredient_ShouldBeSaved() {
        // Given
        Recipe recipeToSave = cteateTestRecipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setTitle("First Ingredient");
        recipeToSave.setRecipeIngredients(Set.of(ingredient));
        Recipe savedRecipe = recipeRepo.save(recipeToSave);
        // Action
        Recipe foundRecipe = recipeRepo.findById(savedRecipe.getId()).orElse(null);

        // Then
        assertNotNull(foundRecipe);
        assertNotNull(foundRecipe.getRecipeIngredients());
    }

    @Test
    void deleteRecipe_success_entityRecipeIsDeleted() {
        // Given
        Recipe recipeToSave = cteateTestRecipe();
        Recipe savedRecipe = recipeRepo.save(recipeToSave);

        // Action
        recipeRepo.deleteById(savedRecipe.getId());

        // Testing
        assertFalse(recipeRepo.findById(savedRecipe.getId()).isPresent());
    }

    @Test
    void updateRecipe__success_ShouldUpdateSuccessfully() {
        // Given
        Recipe recipeToSave = cteateTestRecipe();
        Recipe savedRecipe = recipeRepo.save(recipeToSave);

        // Action
        savedRecipe.setTitle("Updated Recipe");
        Recipe updatedRecipe = recipeRepo.save(savedRecipe);

        // Testing
        assertEquals("Updated Recipe", updatedRecipe.getTitle());
    }

    @Test
    void findById_NonExistingId_ShouldReturnEmptyOptional() {
        // Action
        Recipe foundRecipe = recipeRepo.findById(-1L).orElse(null);

        // Then
        assertNull(foundRecipe);
    }

    @Test
    void saveRecipe_validationError_shouldFireException() {
        // Given
        Recipe recipeToSave = cteateTestRecipe();
        recipeToSave.setInstructions(null);

        // Action
        assertThrows(DataIntegrityViolationException.class, () -> recipeRepo.save(recipeToSave));

    }

    private Recipe cteateTestRecipe() {
        Recipe recipe = new Recipe();
        recipe.setTitle("Test Recipe");
        recipe.setServings(4);
        recipe.setInstructions("Test instructions");
        recipe.setIsVegetarian(true);

        return recipe;
    }
}