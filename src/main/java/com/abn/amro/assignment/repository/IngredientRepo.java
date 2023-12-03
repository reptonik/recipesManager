package com.abn.amro.assignment.repository;

import com.abn.amro.assignment.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepo extends JpaRepository<Ingredient, Long> {
}
