package com.abn.amro.assignment.service;

import com.abn.amro.assignment.exception.NotValidInput;
import com.abn.amro.assignment.exception.RecipeNotFoundException;
import com.abn.amro.assignment.model.domain.IngredientDTO;
import com.abn.amro.assignment.model.domain.RecipeDTO;
import com.abn.amro.assignment.model.entity.Ingredient;
import com.abn.amro.assignment.model.entity.Recipe;
import com.abn.amro.assignment.model.mapper.IngredientMapper;
import com.abn.amro.assignment.repository.IngredientRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepo repo;
    private final IngredientMapper mapper;


    public IngredientDTO getById(Long id) {
        return repo.findById(id)
                .map(mapper::mapToIngredientDto)
                .orElseThrow(() -> new RecipeNotFoundException("Ingredient not found"));
    }

    public List<IngredientDTO> getAll(int page, int size) {
        Pageable request = PageRequest.of(page, size);
        return repo.findAll(request).get()
                .map(mapper::mapToIngredientDto).collect(Collectors.toList());
    }


    public IngredientDTO create(IngredientDTO ingredientDTO) {
        return mapper.mapToIngredientDto(repo.save(mapper.mapToIngredientEntity(ingredientDTO)));
    }

    public IngredientDTO update(IngredientDTO ingredientDTO) {
        if (ingredientDTO.hasId()) {
            repo.findById(ingredientDTO.getId())
                    .orElseThrow(() -> new RecipeNotFoundException("Ingredient not found"));
            return this.create(ingredientDTO);
        } else {
            throw new NotValidInput("No recipe Id found for update");

        }
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
