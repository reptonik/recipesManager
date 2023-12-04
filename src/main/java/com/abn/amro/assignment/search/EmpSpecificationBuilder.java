package com.abn.amro.assignment.search;

import com.abn.amro.assignment.model.entity.Recipe;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmpSpecificationBuilder {
    private final List<SearchCriteria> params;

    public EmpSpecificationBuilder(){
        this.params = new ArrayList<>();
    }

    public final void with(SearchCriteria searchCriteria){
        params.add(searchCriteria);
    }

    public Specification<Recipe> build(String searchOption){
        if(params.isEmpty()){
            return null;
        }

        Specification<Recipe> result = new RecipeSpecification(params.get(0));

        for (int idx = 1; idx < params.size(); idx++){
            SearchCriteria criteria = params.get(idx);
            result = SearchOperation.getSerachOption(searchOption) == SearchOperation.ALL
                    ? Specification.where(result).and(new RecipeSpecification(criteria))
                    : Specification.where(result).or(new RecipeSpecification(criteria));
        }
        return result;
    }
}
