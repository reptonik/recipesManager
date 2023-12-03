package com.abn.amro.assignment.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(of = "id")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    private Integer servings;

    @Column(nullable = false)
    private String instructions;

    private Boolean isVegetarian;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> recipeIngredients;

}
