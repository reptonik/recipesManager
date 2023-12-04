package com.abn.amro.assignment.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 50)
    @EqualsAndHashCode.Include
    private String title;

    @EqualsAndHashCode.Include
    private Integer servings;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String instructions;

    @EqualsAndHashCode.Include
    private Boolean isVegetarian;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    @EqualsAndHashCode.Exclude
    private Set<Ingredient> recipeIngredients;

}
