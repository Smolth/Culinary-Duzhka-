package com.culinar.demo.model;

public class Recept {
    private int id;
    private String head;
    private String image;
    private String[] ingredients;
    private String[] preparation;
    private String[] recipe;
    private String adding;

    public Recept(){}
    public Recept(int id, String head, String image, String[] ingredients, String[] preparation, String[] recipe, String adding) {
        this.id = id;
        this.head = head;
        this.image = image;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.recipe = recipe;
        this.adding = adding;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getImage() {return image;}

    public void setImage(String image) {this.image = image;}

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getPreparation() {
        return preparation;
    }

    public void setPreparation(String[] preparation) {
        this.preparation = preparation;
    }

    public String[] getRecipe() {
        return recipe;
    }

    public void setRecipe(String[] recipe) {
        this.recipe = recipe;
    }

    public String getAdding() {
        return adding;
    }

    public void setAdding(String adding) {
        this.adding = adding;
    }


}
