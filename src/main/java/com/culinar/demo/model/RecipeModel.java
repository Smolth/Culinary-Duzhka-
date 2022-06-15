package com.culinar.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RecipeModel {
    private String head;
    private String[] ingredients;
    private String[] preparation;
    private String[] recipe;
}
