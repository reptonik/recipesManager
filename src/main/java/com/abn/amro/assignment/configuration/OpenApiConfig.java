package com.abn.amro.assignment.configuration;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi recipeApi() {
        return GroupedOpenApi.builder()
                .group("recipe")
                .addOpenApiCustomiser(api -> api.setInfo(new Info().title("Recipe API").version("1.0")))
                .pathsToMatch("/v1/api/recipe/**")
                .build();
    }

    @Bean
    public GroupedOpenApi ingredientApi() {
        return GroupedOpenApi.builder()
                .group("ingredient")
                .addOpenApiCustomiser(api -> api.setInfo(new Info().title("Ingredient API").version("1.0")))
                .pathsToMatch("/v1/api/ingredient/**")
                .build();
    }
}
