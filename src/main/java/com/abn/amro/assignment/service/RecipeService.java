package com.abn.amro.assignment.service;

import com.abn.amro.assignment.exception.NotValidInput;
import com.abn.amro.assignment.exception.RecipeNotFoundException;
import com.abn.amro.assignment.model.domain.RecipeDTO;
import com.abn.amro.assignment.model.mapper.RecipeMapper;
import com.abn.amro.assignment.repository.RecipeRepo;
import com.abn.amro.assignment.search.EmpSpecificationBuilder;
import com.abn.amro.assignment.search.RecipeSearchRequest;
import com.abn.amro.assignment.search.SearchCriteria;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for maintaining recipe data: Adding, updating and fetching.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {

    private final RecipeRepo recipeRepo;
    private final RecipeMapper mapper;



    public RecipeDTO fetchById(Long id) {
        MDC.put("RecipeId", String.valueOf(id));
        return recipeRepo.findById(id)
                .map(mapper::mapToRecipeDto)
                .orElseThrow(() -> {
                    log.error("Recipe not found");
                    return new RecipeNotFoundException("Recipe not found");
                });
    }

    public List<RecipeDTO> fetchAll(int page, int size) {
        Pageable request = PageRequest.of(page, size);
        return recipeRepo.findAll(request).get()
                .map(mapper::mapToRecipeDto).collect(Collectors.toList());
    }

    public List<RecipeDTO> search (RecipeSearchRequest searchDto, int page, int size) {
        EmpSpecificationBuilder builder = new
                EmpSpecificationBuilder();
        List<SearchCriteria> criteriaList =
                searchDto.getSearchCriteriaList();
        if(criteriaList != null){
            criteriaList.forEach(builder::with);
        }


        Pageable request = PageRequest.of(page, size, Sort.by("title").ascending());
        return recipeRepo.findAll(builder.build(searchDto.getSearchOption()), request).get()
                .map(mapper::mapToRecipeDto).collect(Collectors.toList());
    }


    public RecipeDTO create(RecipeDTO recipeDTO) {
        return mapper.mapToRecipeDto(recipeRepo.save(mapper.mapToRecipeEntity(recipeDTO)));
    }


    public RecipeDTO update(RecipeDTO updatedRecipe) {
        if(updatedRecipe.hasId()) {
            recipeRepo.findById(updatedRecipe.getId())
                    .orElseThrow(()->new RecipeNotFoundException("Recipe with ID: " + updatedRecipe.getId() + " found"));
            return create(updatedRecipe);
        } else {
            throw new NotValidInput("No recipe Id found for update");
        }
    }

    public void deleteById(Long id) {
        recipeRepo.deleteById(id);
    }
}
