package com.abn.amro.assignment.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String title;

    @ManyToMany(mappedBy = "recipeIngredients", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Recipe> recipeIngredients;
}
