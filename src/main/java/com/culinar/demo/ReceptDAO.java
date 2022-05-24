package com.culinar.demo;

import com.culinar.demo.model.Recept;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class ReceptDAO {
    private static int count;
    private static final String URL = "jdbc:postgresql://localhost:5431/postgres";
    private static final String username = "evgenia.udalova";
    private static final String password = "";

    private static Connection connection;
    
    private static boolean flag = false;

    static {
        try {
            connection = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Recept> index() throws SQLException {
        List<Recept> recipes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "Select * from recept";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Recept recept = new Recept();

                recept.setId(resultSet.getInt("id"));
                recept.setHead(resultSet.getString("head"));

                Array ingredients = resultSet.getArray(3);
                String[] str_ingredients = (String[])ingredients.getArray();
                recept.setIngredients(str_ingredients);

                Array preparation = resultSet.getArray(4);
                String[] str_preparation = (String[])preparation.getArray();
                recept.setPreparation(str_preparation);

                Array recipe = resultSet.getArray(5);
                String[] str_recipe = (String[])recipe.getArray();
                recept.setRecipe(str_recipe);
                
                recept.setAdding(resultSet.getString("adding"));

                recipes.add(recept);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipes;
    }
    public Recept show(int id) {
        Recept recept = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from recept where id=?");
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        recept = new Recept();
        recept.setId(resultSet.getInt("id"));
        recept.setHead(resultSet.getString("head"));

        Array ingredients = resultSet.getArray(3);
        String[] str_ingredients = (String[])ingredients.getArray();
        recept.setIngredients(str_ingredients);

        Array preparation = resultSet.getArray(4);
        String[] str_preparation = (String[])preparation.getArray();
        recept.setPreparation(str_preparation);

        Array recipe = resultSet.getArray(5);
        String[] str_recipe = (String[])recipe.getArray();
        recept.setRecipe(str_recipe);
            
        recept.setAdding(resultSet.getString("adding"));    

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recept;
    }
}

public List<Recept> searchByIngredients(String[] ingredients) {
        this.flag = false;
        String x = "";
        List<Recept> findingRecipes = new ArrayList<>();
        List<Recept> similarRecipes = new ArrayList<>();
        ArrayList<Integer> idOfRecipes = new ArrayList<>();
        ArrayList<Integer> idOfSimilarRecipes = new ArrayList<>();
        ArrayList<String> userIngredients;
        int id;

        try {
            ArrayList<String> products;
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "Select id, ingredients " +
                    "from recept");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                products = new ArrayList<>();
                id = rs.getInt("id");
                Array dataIngredients = rs.getArray("ingredients");
                String[] str_ingredients = (String[]) dataIngredients.getArray();
                for (String i : str_ingredients) {
                    products.add(i);
                }

                userIngredients = new ArrayList<>();
                for (String i : ingredients) {
                    userIngredients.add(i);
                }
                /*PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "Select * " +
                        "from   recept " +
                        "where  ingredients " +
                        "similar to '%?%';");*/

                /*for (int i = 0; i < ingredients.length; i++) {
                    products.add(ingredients[i]);
                }

                Log log = LogFactory.getLog(this.getClass());
                String x = "";
                log.info(x);
                 for(String i : ingredients){
                    x = x.concat(i).concat(",");
                 }*/
                    for (int j = 0; j < products.size(); j++) {
                        //int counter = 0;

                        for (int k = 0; k < userIngredients.size(); k++) {
                            String s = userIngredients.get(k);
                            if (products.get(j).contains(s)) {
                                products.remove(j);
                                userIngredients.remove(k);
                                k--;
                                j--;
                                break;
                            }else if (products.get(j).contains("по желанию")){
                                products.remove(j);
                                j--;
                                break;
                            }
                        }
                        if (products.isEmpty()) {
                            idOfRecipes.add(id);
                            this.flag = true;
                            break;
                        }
                    }
                    if ((products.size() < 5) & (!this.flag)) {
                            idOfSimilarRecipes.add(id);
                            for(String i : products){
                                x = x.concat(i).concat("; ");
                            }
                            String message = "Необходимо добавить следующие ингредиенты: " + x;
                            PreparedStatement ps = connection.prepareStatement("" +
                                    "UPDATE recept " +
                                    "SET adding = ? " +
                                    "WHERE id = ?;");

                            ps.setString(1, message);
                            ps.setInt(2, id);
                            ps.executeUpdate();
                    }
                }
                if (this.flag) {
                    for (int number : idOfRecipes) {
                        findingRecipes.add(this.show(number));
                    }
                    return findingRecipes;
                } else {
                    for (int number : idOfSimilarRecipes) {
                        similarRecipes.add(this.show(number));
                    }
                    return similarRecipes;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

