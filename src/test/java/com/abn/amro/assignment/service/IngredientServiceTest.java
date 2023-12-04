package com.abn.amro.assignment.service;

import com.abn.amro.assignment.exception.NotValidInput;
import com.abn.amro.assignment.exception.RecipeNotFoundException;
import com.abn.amro.assignment.model.domain.IngredientDTO;
import com.abn.amro.assignment.model.entity.Ingredient;
import com.abn.amro.assignment.model.mapper.IngredientMapper;
import com.abn.amro.assignment.model.mapper.IngredientMapperImpl;
import com.abn.amro.assignment.repository.IngredientRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {
    @Mock
    private IngredientRepo ingredientRepo;

    @Spy
    private IngredientMapper ingredientMapper = new IngredientMapperImpl(); // setup actual mapper

    @InjectMocks
    private IngredientService ingredientService;


    @Test
    void getById_IngredientFound_ReturnsIngredientDTO() {
        var ingredientId = 1L;
        var ingredientEntity = new Ingredient();
        ingredientEntity.setId(ingredientId);
        var expectedDto = new IngredientDTO();
        expectedDto.setId(ingredientId);

        when(ingredientRepo.findById(ingredientId)).thenReturn(Optional.of(ingredientEntity));

        var result = ingredientService.getById(ingredientId);

        assertEquals(expectedDto, result);
    }

    @Test
    void getById_IngredientNotFound_ThrowsRecipeNotFoundException() {
        var ingredientId = 1L;

        when(ingredientRepo.findById(ingredientId)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> ingredientService.getById(ingredientId));
    }

    @Test
    void getAll_ReturnsListOfIngredientDTO() {
        var page = 0;
        var size = 10;
        var request = PageRequest.of(page, size);
        var ingredientEntity = new Ingredient();
        var expectedDto = new IngredientDTO();

        when(ingredientRepo.findAll(request)).thenReturn(new PageImpl<>(List.of(ingredientEntity)));

        var result = ingredientService.getAll(page, size);

        assertEquals(Collections.singletonList(expectedDto), result);
    }

    @Test
    void create_ValidIngredientDTO_ReturnsCreatedIngredientDTO() {
        var inputDto = new IngredientDTO();
        var expectedDto = new IngredientDTO();

        when(ingredientRepo.save(any(Ingredient.class))).thenReturn(new Ingredient());

        var result = ingredientService.create(inputDto);

        assertEquals(expectedDto, result);
    }

    @Test
    void update_ExistingIngredientDTO_ReturnsUpdatedIngredientDTO() {
        var inputDto = new IngredientDTO();
        inputDto.setId(1L);
        var expectedDto = new IngredientDTO();
        expectedDto.setId(1L);

        var entity = new Ingredient();
        entity.setId(1L);

        when(ingredientRepo.findById(1L)).thenReturn(Optional.of(entity));
        when(ingredientRepo.save(entity)).thenReturn(entity);


        var result = ingredientService.update(inputDto);

        assertEquals(expectedDto, result);
    }

    @Test
    void update_NonexistentIngredientDTO_ThrowsRecipeNotFoundException() {

        var inputDto = new IngredientDTO();
        inputDto.setId(1L);

        when(ingredientRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> ingredientService.update(inputDto));
    }

    @Test
    void update_IngredientDTOWithoutId_ThrowsNotValidInputException() {
        var inputDto = new IngredientDTO();

        assertThrows(NotValidInput.class, () -> ingredientService.update(inputDto));
    }

    @Test
    void deleteById_IngredientFound_DeletesIngredient() {
        var ingredientId = 1L;

        ingredientService.deleteById(ingredientId);

        verify(ingredientRepo, times(1)).deleteById(ingredientId);
    }
}