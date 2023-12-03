package com.abn.amro.assignment.repository;

import com.abn.amro.assignment.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepo extends JpaRepository<Recipe, Long> {
}
