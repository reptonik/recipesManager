package com.abn.amro.assignment.repository;

import com.abn.amro.assignment.model.entity.Ingredient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IngredientRepoTest {

    @Autowired
    private IngredientRepo ingredientRepo;

    @Test
    void saveIngredient_ShouldPersistSuccessfully() {
        Ingredient ingredientToSave = createTestIngredient();

        Ingredient savedIngredient = ingredientRepo.save(ingredientToSave);

        assertNotNull(savedIngredient.getId());
        assertEquals("Test Ingredient", savedIngredient.getTitle());
    }

    @Test
    void findById_ExistingId_ShouldReturnIngredient() {
        Ingredient ingredientToSave = createTestIngredient();
        Ingredient savedIngredient = ingredientRepo.save(ingredientToSave);

        Ingredient foundIngredient = ingredientRepo.findById(savedIngredient.getId()).orElse(null);

        assertNotNull(foundIngredient);
        assertEquals(savedIngredient.getId(), foundIngredient.getId());
        assertEquals(savedIngredient.getTitle(), foundIngredient.getTitle());
        // Add more assertions based on your entity structure
    }

    @Test
    void findById_NonExistingId_ShouldReturnEmptyOptional() {
        Ingredient foundIngredient = ingredientRepo.findById(-1L).orElse(null);

        assertNull(foundIngredient);
    }

    @Test
    void updateIngredient_ShouldUpdateSuccessfully() {
        Ingredient ingredientToSave = createTestIngredient();
        Ingredient savedIngredient = ingredientRepo.save(ingredientToSave);

        savedIngredient.setTitle("Updated Ingredient");
        Ingredient updatedIngredient = ingredientRepo.save(savedIngredient);

        assertEquals("Updated Ingredient", updatedIngredient.getTitle());
    }

    @Test
    void deleteIngredient_ShouldDeleteSuccessfully() {
        Ingredient ingredientToSave = createTestIngredient();
        Ingredient savedIngredient = ingredientRepo.save(ingredientToSave);

        ingredientRepo.deleteById(savedIngredient.getId());

        assertFalse(ingredientRepo.existsById(savedIngredient.getId()));
    }

    private Ingredient createTestIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setTitle("Test Ingredient");
        return ingredient;
    }
}