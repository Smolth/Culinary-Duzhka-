package com.culinar.demo.repository;

import com.culinar.demo.model.RecipeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptRepository extends CrudRepository<RecipeEntity, Integer>, RecipeCustomMethods {
    RecipeEntity save(RecipeEntity recipeEntity);
}
