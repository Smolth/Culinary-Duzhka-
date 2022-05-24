package com.culinar.demo.Controller;

import com.culinar.demo.ReceptDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
public class ReceptController {
    @Autowired
    private ReceptDAO receptDAO;


    @GetMapping("/allRecipes")
        public String index (Model model) throws SQLException {
        model.addAttribute("recipes", receptDAO.index());
        return "output/recepts";
    }
    @GetMapping("/{id}")
    public String show (@PathVariable ("id") int id, Model model) {
        model.addAttribute("recept", receptDAO.show(id));
        return "output/recept";
    }

    @PostMapping()
    public String submit(HttpServletRequest request, Model model,
                         @RequestParam(name="ingredients", required = false) String[] ingredients){
        ingredients = request.getParameterValues("ingredients");
        model.addAttribute("recipes", receptDAO.searchByIngredients(ingredients));
        if (receptDAO.isFlag()) {
            return "output/recepts";
        } else return "output/recipes";
    }
}
