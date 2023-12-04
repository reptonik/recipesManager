package com.abn.amro.assignment.search;

import com.abn.amro.assignment.model.entity.Ingredient;
import com.abn.amro.assignment.model.entity.Recipe;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class RecipeSpecification implements Specification<Recipe> {

    private static final String RECIPE_INGREDIENT_TABLE = "recipeIngredients";

    private final SearchCriteria searchCriteria;

    public RecipeSpecification(SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }


    @Override
    public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = searchCriteria.getValue()
                .toString().toLowerCase();


        query.distinct(true);
        return switch(Objects.requireNonNull(
                SearchOperation.getSimpleOperation
                        (searchCriteria.getOperation()))){
            case CONTAINS -> {
                if(searchCriteria.isIngredient()){
                    yield cb.like(cb.lower(ingredientJoin(root).
                                    get(searchCriteria.getFilterKey()).as(String.class)),
                            "%" + strToSearch + "%");
                }
                yield cb.like(cb.lower(root
                                .get(searchCriteria.getFilterKey()).as(String.class)),
                        "%" + strToSearch + "%");
            }
            case DOES_NOT_CONTAIN -> {
                if(searchCriteria.isIngredient()){
                    yield cb.notLike(cb.lower(ingredientJoin(root).
                                    get(searchCriteria.getFilterKey()).as(String.class)),
                            "%" + strToSearch + "%");
                }
                yield cb.notLike(cb.lower(root
                                .get(searchCriteria.getFilterKey()).as(String.class)),
                        "%" + strToSearch + "%");
            }
            case EQUAL -> {
                if(searchCriteria.isIngredient()){
                    yield cb.equal(cb.lower(ingredientJoin(root).
                                    get(searchCriteria.getFilterKey()).as(String.class)),
                            "%" + strToSearch + "%");
                }
                yield cb.equal(cb.lower(root
                                .get(searchCriteria.getFilterKey()).as(String.class)),
                        "%" + strToSearch + "%");
            }
            case NOT_EQUAL -> {
                if(searchCriteria.isIngredient()){
                    yield cb.notEqual(cb.lower(ingredientJoin(root).
                                    get(searchCriteria.getFilterKey()).as(String.class)),
                            "%" + strToSearch + "%");
                }
                yield cb.notEqual(cb.lower(root
                                .get(searchCriteria.getFilterKey()).as(String.class)),
                        "%" + strToSearch + "%");
            }
            case ANY -> null;
            case ALL -> null;
        };
    }

    private Join<Recipe, Ingredient> ingredientJoin(Root<Recipe> root){
        return root.join(RECIPE_INGREDIENT_TABLE, JoinType.INNER);
    }
}
