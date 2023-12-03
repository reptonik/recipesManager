package com.abn.amro.assignment.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(of = "id")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @ManyToMany(mappedBy = "recipeIngredients", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Recipe> recipeIngredients;
}
