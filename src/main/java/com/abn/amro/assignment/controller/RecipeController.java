package com.abn.amro.assignment.controller;

import com.abn.amro.assignment.model.domain.RecipeDTO;
import com.abn.amro.assignment.search.RecipeSearchRequest;
import com.abn.amro.assignment.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
public class RecipeController {

    private final RecipeService service;

    @Operation(summary = "Get a recipe by ID")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Recipe not found")
    @GetMapping("/recipe/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable Long id) {
        log.info("Get a recipe id: {}", id);
        return new ResponseEntity<>(service.fetchById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get list of recipes")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/recipe/page/{page}/size{size}")
    public ResponseEntity<List<RecipeDTO>> getListOfRecipe(@PathVariable int page, @PathVariable int size) {
        log.info("Getting list of recipes page: {} and size: {}", page, size);
        return new ResponseEntity<>(service.fetchAll(page, size), HttpStatus.OK);
    }

    @Operation(summary = "Create a new recipe")
    @ApiResponse(responseCode = "201", description = "Recipe created successfully")
    @PostMapping("/recipe")
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeDTO recipeDTO) {
        log.info("Creating a new recipe");
        return new ResponseEntity<>(service.create(recipeDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a recipe by ID")
    @ApiResponse(responseCode = "200", description = "Recipe updated successfully")
    @ApiResponse(responseCode = "404", description = "Recipe not found")
    @PutMapping("/recipe")
    public ResponseEntity<RecipeDTO> updateRecipe(@RequestBody RecipeDTO recipeDTO) {
        log.info("Updating a new recipe");
        return new ResponseEntity<>(service.update(recipeDTO), HttpStatus.OK);
    }

    @Operation(summary = "Delete a recipe by ID")
    @ApiResponse(responseCode = "200", description = "Recipe updated successfully")
    @ApiResponse(responseCode = "404", description = "Recipe not found")
    @DeleteMapping("/recipe")
    public ResponseEntity<Void> deleteRecipe(@RequestParam @NotNull(message = "Id is required") Long id) {
        log.info("Deleting a recipe id: {}", id);
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Search a recipe by Criteria")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Recipe not found")
    @PostMapping("/recipe/search")
    ResponseEntity<List<RecipeDTO>> searchRecipe(@RequestParam int page, @RequestParam int size,
                                                 @RequestBody @Valid RecipeSearchRequest searchDto) {
        log.info("Searching for the recipe with criteria: {}", searchDto);
        return new ResponseEntity<>(service.search(searchDto, page, size), HttpStatus.OK);
    }
}
