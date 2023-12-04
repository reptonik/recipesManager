package com.abn.amro.assignment.integration;

import com.abn.amro.assignment.model.domain.IngredientDTO;
import com.abn.amro.assignment.model.domain.RecipeDTO;
import com.abn.amro.assignment.search.RecipeSearchRequest;
import com.abn.amro.assignment.search.SearchCriteria;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getRecipe_ReturnsRecipeDTO() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/recipe/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        RecipeDTO recipeDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RecipeDTO.class);
        assertThat(recipeDTO).isNotNull();
        assertThat(recipeDTO.getId()).isEqualTo(1L);

    }

    @Test
    void getRecipe_notExistReturnsError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/recipe/{id}", 1000L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getListOfRecipe_ReturnsListOfRecipeDTO() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/recipe/page/{page}/size{size}", 0, 10))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<RecipeDTO> recipeDTOList = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });


        assertThat(recipeDTOList).isNotNull();
        assertThat(recipeDTOList.size()).isBetween(0, 11);
    }

    @Test
    void createRecipe_WithValidData_Returns201() throws Exception {
        RecipeDTO validRecipeDTO = new RecipeDTO();
        validRecipeDTO.setTitle("Spaghetti Bolognese Incredible");
        validRecipeDTO.setServings(4);
        validRecipeDTO.setInstructions("Cook spaghetti and prepare Bolognese sauce.");
        validRecipeDTO.setIsVegetarian(false);

        // Add valid ingredients
        IngredientDTO ingredient1 = new IngredientDTO();
        ingredient1.setTitle("Tomato Sauce chili");

        IngredientDTO ingredient2 = new IngredientDTO();
        ingredient2.setTitle("green Spaghetti");

        IngredientDTO ingredient3 = new IngredientDTO();
        ingredient3.setTitle("Red Beef");

        validRecipeDTO.setRecipeIngredients(Set.of(ingredient1, ingredient2, ingredient3));

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRecipeDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Spaghetti Bolognese Incredible"));
    }

    @Test
    void createRecipe_WithInvalidData_Returns400() throws Exception {
        RecipeDTO invalidRecipeDTO = new RecipeDTO();
        invalidRecipeDTO.setServings(4);
        invalidRecipeDTO.setInstructions("Cook spaghetti and prepare Bolognese sauce.");
        invalidRecipeDTO.setIsVegetarian(false);

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRecipeDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateRecipe_WithValidData_Returns200() throws Exception {
        // Create a valid RecipeDTO for update
        RecipeDTO validRecipeDTO = new RecipeDTO();
        validRecipeDTO.setId(1L);
        validRecipeDTO.setTitle("Updated Spaghetti Bolognese");
        validRecipeDTO.setServings(5);
        validRecipeDTO.setInstructions("Updated instructions.");
        validRecipeDTO.setIsVegetarian(true);

        // Add valid ingredients
        IngredientDTO ingredient1 = new IngredientDTO();
        ingredient1.setId(3L);
        ingredient1.setTitle("Updated Tomato Sauce");

        IngredientDTO ingredient2 = new IngredientDTO();
        ingredient2.setId(1L);
        ingredient2.setTitle("Updated Spaghetti");

        IngredientDTO ingredient3 = new IngredientDTO();
        ingredient3.setId(2L);
        ingredient3.setTitle("Updated Beef");

        validRecipeDTO.setRecipeIngredients(Set.of(ingredient1, ingredient2, ingredient3));

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRecipeDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(validRecipeDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Spaghetti Bolognese"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.servings").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instructions").value("Updated instructions."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isVegetarian").value(true));
    }

    @Test
    void updateRecipe_WithNonexistentId_Returns404() throws Exception {
        // Create a RecipeDTO with a non-existent ID for update
        RecipeDTO invalidRecipeDTO = new RecipeDTO();
        invalidRecipeDTO.setId(999L); // Assuming there is no recipe with ID 999

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRecipeDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteRecipe_WithExistingId_Returns200() throws Exception {
        // Assuming there is a recipe with ID 1 for deletion
        Long existingRecipeId = 1L;

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/recipe")
                        .param("id", String.valueOf(existingRecipeId))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void searchRecipe_WithValidCriteria_Returns200() throws Exception {
        // Create a valid RecipeSearchRequest
        RecipeSearchRequest validSearchRequest = new RecipeSearchRequest();
        validSearchRequest.setSearchCriteriaList(List.of(
                new SearchCriteria("title", "Spaghetti", "cn", false),
                new SearchCriteria("servings", 4, "eq", false),
                new SearchCriteria("title", "Tomato", "cn", true)
        ));
        validSearchRequest.setSearchOption("all");

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/recipe/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSearchRequest))
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void searchRecipe_WithInvalidCriteria_Returns400() throws Exception {
        // Create an invalid RecipeSearchRequest with unknown filter key
        RecipeSearchRequest invalidSearchRequest = new RecipeSearchRequest();
        invalidSearchRequest.setSearchCriteriaList(List.of(
                new SearchCriteria("unknownField", "value", "eq", false)
        ));
        invalidSearchRequest.setSearchOption("all");

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/recipe/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSearchRequest))
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
