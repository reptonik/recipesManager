package com.abn.amro.assignment.service;

import com.abn.amro.assignment.exception.NotValidInput;
import com.abn.amro.assignment.exception.RecipeNotFoundException;
import com.abn.amro.assignment.model.domain.IngredientDTO;
import com.abn.amro.assignment.model.domain.RecipeDTO;
import com.abn.amro.assignment.model.entity.Recipe;
import com.abn.amro.assignment.model.mapper.RecipeMapper;
import com.abn.amro.assignment.model.mapper.RecipeMapperImpl;
import com.abn.amro.assignment.repository.RecipeRepo;
import com.abn.amro.assignment.search.RecipeSearchRequest;
import com.abn.amro.assignment.search.SearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {
    @Mock
    private RecipeRepo recipeRepo;

    @Spy
    private RecipeMapper recipeMapper = new RecipeMapperImpl();

    @InjectMocks
    private RecipeService recipeService;



    @Test
    void fetchById_RecipeFound_ReturnsRecipeDTO() {
        var recipeId = 1L;

        var expectedDto = createDTO(recipeId);
        var entity = createEntity(recipeId);


        when(recipeRepo.findById(recipeId)).thenReturn(Optional.of(entity));

        var result = recipeService.fetchById(recipeId);

        assertEquals(expectedDto, result);
    }

    @Test
    void fetchById_RecipeNotFound_ThrowsRecipeNotFoundException() {
        var recipeId = 1L;

        when(recipeRepo.findById(recipeId)).thenReturn(Optional.empty());


        assertThrows(RecipeNotFoundException.class, () -> recipeService.fetchById(recipeId));
    }

    @Test
    void fetchAll_ReturnsListOfRecipeDTO() {
        var recipeId = 1L;
        var page = 0;
        var size = 10;
        var request = PageRequest.of(page, size);
        var expectedDto = createDTO(recipeId);
        var entity = createEntity(recipeId);


        when(recipeRepo.findAll(request)).thenReturn(new PageImpl<>(List.of(entity)));


        var result = recipeService.fetchAll(page, size);


        assertEquals(Collections.singletonList(expectedDto), result);
    }

    @Test
    void search_ReturnsListOfRecipeDTO() {
        var recipeId = 1L;
        var searchDto = new RecipeSearchRequest();
        searchDto.setSearchCriteriaList(List.of(new SearchCriteria()));
        var page = 0;
        var size = 10;
        var expectedDto = createDTO(recipeId);
        var entity = createEntity(recipeId);

        Pageable request = PageRequest.of(page, size, Sort.by("title").ascending());
        when(recipeRepo.findAll(any(Specification.class), eq(request))).thenReturn(new PageImpl<>(Collections.singletonList(entity)));


        var result = recipeService.search(searchDto, page, size);


        assertEquals(Collections.singletonList(expectedDto), result);
    }

    @Test
    void create_ValidRecipeDTO_ReturnsCreatedRecipeDTO() {
        var recipeId = 1L;
        var inputDto = createDTO(recipeId);
        var expectedDto = createDTO(recipeId);

        var entity = createEntity(recipeId);

        when(recipeRepo.save(any(Recipe.class))).thenReturn(entity);

        var result = recipeService.create(inputDto);


        assertEquals(expectedDto, result);
    }

    @Test
    void update_ExistingRecipeDTO_ReturnsUpdatedRecipeDTO() {
        var recipeId = 1L;
        var inputDto = createDTO(recipeId);
        var expectedDto = createDTO(recipeId);

        var entity = createEntity(recipeId);

        when(recipeRepo.findById(1L)).thenReturn(Optional.of(entity));
        when(recipeRepo.save(any())).thenReturn(entity);

        var result = recipeService.update(inputDto);

        // Assert
        assertEquals(expectedDto, result);
    }

    @Test
    void update_NonexistentRecipeDTO_ThrowsRecipeNotFoundException() {
        var inputDto = new RecipeDTO();
        inputDto.setId(1L);

        when(recipeRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> recipeService.update(inputDto));
    }

    @Test
    void update_RecipeDTOWithoutId_ThrowsNotValidInputException() {
        RecipeDTO inputDto = new RecipeDTO();

        assertThrows(NotValidInput.class, () -> recipeService.update(inputDto));
    }

    @Test
    void deleteById_RecipeFound_DeletesRecipe() {
        var recipeId = 1L;

        recipeService.deleteById(recipeId);

        verify(recipeRepo, times(1)).deleteById(recipeId);
    }

    private RecipeDTO createDTO(Long recipeId) {
        var ingredient = new IngredientDTO();
        ingredient.setId(recipeId);
        ingredient.setTitle("Test ingredient");
        var expectedDto = new RecipeDTO();
        expectedDto.setId(recipeId);
        expectedDto.setIsVegetarian(true);
        expectedDto.setInstructions("This is test instructions");
        expectedDto.setServings(3);
        expectedDto.setRecipeIngredients(Set.of(ingredient));

        return expectedDto;
    }

    private Recipe createEntity(Long entityId) {
        return recipeMapper.mapToRecipeEntity(createDTO(entityId));
    }
}