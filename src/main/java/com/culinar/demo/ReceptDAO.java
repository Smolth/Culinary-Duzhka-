package com.culinar.demo;

import com.culinar.demo.model.Recept;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Component
public class ReceptDAO {
    //private static int count = 0;
    private static final String URL = "jdbc:postgresql://localhost:5431/postgres";
    private static final String username = "evgenia.udalova";
    private static final String password = "";
    private static final Connection connection;
    private static boolean flag;
    private static final short limit = 4;


    static {
        try {
            connection = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isFlag() {
        return flag;
    }

    public List<Recept> index() {
        List<Recept> recipes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "Select * from recept";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Recept recept = new Recept();

                recept.setId(resultSet.getInt("id"));
                recept.setHead(resultSet.getString("head"));

                recept.setImage("/img/" + resultSet.getString(6));

                Array ingredients = resultSet.getArray(3);
                String[] str_ingredients = (String[]) ingredients.getArray();
                recept.setIngredients(str_ingredients);

                Array preparation = resultSet.getArray(4);
                String[] str_preparation = (String[]) preparation.getArray();
                recept.setPreparation(str_preparation);

                Array recipe = resultSet.getArray(5);
                String[] str_recipe = (String[]) recipe.getArray();
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
            if (resultSet.next()) {
                recept = new Recept();

                recept.setId(resultSet.getInt("id"));
                recept.setHead(resultSet.getString("head"));

                recept.setImage("/img/" + resultSet.getString(6));

                Array ingredients = resultSet.getArray(3);
                String[] str_ingredients = (String[]) ingredients.getArray();
                recept.setIngredients(str_ingredients);

                Array preparation = resultSet.getArray(4);
                String[] str_preparation = (String[]) preparation.getArray();
                recept.setPreparation(str_preparation);

                Array recipe = resultSet.getArray(5);
                String[] str_recipe = (String[]) recipe.getArray();
                recept.setRecipe(str_recipe);

                recept.setAdding(resultSet.getString("adding"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recept;
    }

    public List<Recept> searchByIngredients(String[] ingredients) {
        flag = false;

        Array dataIngredients;
        String[] str_ingredients;
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
                String x = "";
                products = new ArrayList<>();
                id = rs.getInt("id");
                dataIngredients = rs.getArray("ingredients");
                str_ingredients = (String[]) dataIngredients.getArray();
                Collections.addAll(products, str_ingredients);

                userIngredients = new ArrayList<>();
                Collections.addAll(userIngredients, ingredients);
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
                        flag = true;
                        break;
                    }
                }
                if ((products.size() < limit) & (!flag)) {
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

                /*if (findingRecipes.isEmpty()) {
                    this.flag = false;
                    PreparedStatement ps = connection.prepareStatement("Select * from recept where ingredients iLIKE any ?");
                    ps.setString(1, "(" + products + ")");
                    ResultSet rs = ps.executeQuery();
                    while (resultSet.next() & (count < 8)) {
                        count++;
                        recept = new Recept();
                        recept.setId(resultSet.getInt("id"));
                        recept.setHead(resultSet.getString("head"));
                        recept.setImage("![](../../../../resources/images/" + resultSet.getString(6) + ")");
                        Array food = resultSet.getArray(3);
                        String[] str_ingredients = (String[]) food.getArray();
                        recept.setIngredients(str_ingredients);
                        Array preparation = resultSet.getArray(4);
                        String[] str_preparation = (String[]) preparation.getArray();
                        recept.setPreparation(str_preparation);
                        Array recipe = resultSet.getArray(5);
                        String[] str_recipe = (String[]) recipe.getArray();
                        recept.setRecipe(str_recipe);
                        findingRecipes.add(recept);
                    }
                }*/
            }
            if (flag) {
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
    public List<Recept> searchByWord(String head) {
        List<Recept> recipes = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Select id, head, ingredients from recept where head ilike (?) or array_to_string(ingredients, ', ') ilike  (?) ");
            preparedStatement.setString(1,  '%' + head + '%' );
            preparedStatement.setString(2,  '%' + head + '%' );
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Recept recept = new Recept();

                recept.setId(resultSet.getInt("id"));
                recept.setHead(resultSet.getString("head"));


                Array ingredients = resultSet.getArray("ingredients");
                String[] str_ingredients = (String[]) ingredients.getArray();
                recept.setIngredients(str_ingredients);

                String x = "";
                for(String i : str_ingredients){
                    x = x.concat(i).concat("; ");
                }

                String message = "Необходимо добавить следующие ингредиенты: " + x;
                recept.setAdding(message);

                recipes.add(recept);
            }
            return recipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void save(Recept recipe) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Person VALUES(?, ?, ?, ?, ?, ?)");

            Array arrayIngredients = connection.createArrayOf("text", recipe.getIngredients());
            Array arrayPreparation = connection.createArrayOf("text", recipe.getPreparation());
            Array arrayRecipe = connection.createArrayOf("text", recipe.getRecipe());

            preparedStatement.setInt(1, recipe.getId());
            preparedStatement.setString(2, recipe.getHead());

            preparedStatement.setArray(3, arrayIngredients);
            preparedStatement.setArray(4, arrayPreparation);
            preparedStatement.setArray(5, arrayRecipe);

            preparedStatement.setString(6, recipe.getImage());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}