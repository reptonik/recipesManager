package com.abn.amro.assignment.controller;

import com.abn.amro.assignment.model.domain.IngredientDTO;
import com.abn.amro.assignment.model.domain.RecipeDTO;
import com.abn.amro.assignment.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/")
public class IngredientController {

    private final IngredientService service;

    @Operation(summary = "Get a ingredient by ID")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Ingredient not found")
    @GetMapping("/ingredient/{id}")
    public ResponseEntity<IngredientDTO> getIngredient(@PathVariable Long id) {
        log.info("Get a recipe id: {}", id);
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get list of ingredient")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/ingredient/page/{page}/size{size}")
    public ResponseEntity<List<IngredientDTO>> getListOfRecipe(@PathVariable int page, @PathVariable int size) {
        log.info("Getting list of recipes page: {} and size: {}", page, size);
        return new ResponseEntity<>(service.getAll(page, size), HttpStatus.OK);
    }

    @Operation(summary = "Create a new ingredient")
    @ApiResponse(responseCode = "201", description = "Recipe created successfully")
    @PostMapping("/ingredient")
    public ResponseEntity<IngredientDTO> createRecipe(@RequestBody IngredientDTO ingredientDTO) {
        log.info("Creating a new recipe");
        return new ResponseEntity<>(service.create(ingredientDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a ingredient by ID")
    @ApiResponse(responseCode = "200", description = "Recipe updated successfully")
    @ApiResponse(responseCode = "404", description = "Recipe not found")
    @PutMapping("/ingredient")
    public ResponseEntity<IngredientDTO> updateRecipe(@RequestBody IngredientDTO ingredientDTO) {
        log.info("Updating a new recipe");
        return new ResponseEntity<>(service.update(ingredientDTO), HttpStatus.OK);
    }

    @Operation(summary = "Delete a ingredient by ID")
    @ApiResponse(responseCode = "200", description = "Recipe updated successfully")
    @ApiResponse(responseCode = "404", description = "Recipe not found")
    @DeleteMapping("/ingredient")
    public ResponseEntity<Void> deleteRecipe(@RequestParam @NotNull(message = "Id is required") Long id) {
        log.info("Deleting a recipe id: {}", id);
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
