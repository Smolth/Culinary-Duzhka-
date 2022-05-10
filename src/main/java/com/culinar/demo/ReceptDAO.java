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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recept;
    }
}
