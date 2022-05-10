package com.culinar.demo.model;

public class Recept {
    private int id;
    private String head;
    private String[] ingredients;
    private String[] preparation;
    private String[] recipe;

    public Recept(){;}
    public Recept(int id, String head, String[] ingredients, String[] preparation, String[] recipe) {
        this.id = id;
        this.head = head;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.recipe = recipe;
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

    public void cook(String a) {
        String b;
        switch (a) {
            case "Carrot":
                b = "Link of recept with Carrot";
                break;
            case "Potato":
                b = "Link of recept with Potato";
                break;
            default:
                b = "Link with your ingredient not exist";
        }

    }

}
