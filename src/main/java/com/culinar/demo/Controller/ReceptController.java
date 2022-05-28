package com.culinar.demo.Controller;

import com.culinar.demo.ReceptDAO;
import com.culinar.demo.model.Recept;
import com.culinar.demo.repository.ReceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReceptController {
    @Autowired
    private ReceptDAO receptDAO;

    //private ReceptRepository receptRepository;


    @GetMapping("/allRecipes")
        public String index (Model model){
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
                         @RequestParam(name="ingredients", required = false) String[] ingredients) throws NullPointerException{
        String message;
        try {
            ingredients = request.getParameterValues("ingredients");
            model.addAttribute("recipes", receptDAO.searchByIngredients(ingredients));
            if (receptDAO.isFlag()) {
                return "output/recepts";
            } else {
                if (receptDAO.searchByIngredients(ingredients).isEmpty()){
                message = "Вам следует добавить больше ингредиентов, посмотрите хорошенько в ящиках и не забудьте о праве 'обчистить холодильник'.";
                } else message="Кажется, у вас недостаточно продуктов для создания полноценного блюда, но мы подобрали рецепты блюд, которые пойдойдут вам, если добавить немного ингредиентов:";
                model.addAttribute("message", message);
                return "output/recipes";
            }
        } catch (NullPointerException e)
        {
            return "output/notFound";
        }
    }
    @PostMapping("/allRecipes")
    public String findByWord(@RequestParam ("Word") String word, Model model) {
        String message;
        try {
            List<Recept> receptList = receptDAO.searchByWord(word);
            if (receptList.size() == 1) {
                model.addAttribute("recipes", receptList.get(0));
                return "output/recept";
            } else {
                if (receptList.isEmpty()) {
                    message = "По данному запросу ничего не найдено.";
                } else message = "Вот, что мы нашли по вашему запросу:";
                model.addAttribute("recipes", receptList);
                model.addAttribute("message", message);
                return "output/recipes";
            }
        } catch (NullPointerException e){
            return "output/notFound";
        }
    }
}
