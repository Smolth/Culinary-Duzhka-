package com.culinar.demo.Controller;

import com.culinar.demo.ReceptDAO;
import com.culinar.demo.model.RecipeEntity;
import com.culinar.demo.model.RecipeModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class ReceptController {

    private ReceptDAO receptDAO;


    @GetMapping("/allRecipes")
        public String index (Model model){
        log.info("Вызов полного перечня рецептов");
        model.addAttribute("recipes", receptDAO.index());
        return "output/recepts";
    }
    @GetMapping("/{id}")
    public String show (@PathVariable ("id") int id, Model model) {
        log.info("Поиск рецепта");
        model.addAttribute("recept", receptDAO.show(id));
        return "output/recept";
    }

    @PostMapping()
    public String submit(HttpServletRequest request, Model model,
                         @RequestParam(name="ingredients", required = false) String[] ingredients) throws NullPointerException{
        log.info(ingredients.toString());
        String message;
        try {
            ingredients = request.getParameterValues("ingredients");
            model.addAttribute("recipes", receptDAO.searchByIngredients(ingredients));
            log.info("Список сформирован");
            if (receptDAO.isFlag()) {
                log.info("Предоставление рецептов, составленных из набора ингредиентов запроса");
                return "output/recepts";
            } else {
                log.info("Предоставление частично подходящих пользователю рецептов");
                if (receptDAO.searchByIngredients(ingredients).isEmpty()){
                    log.info("Предъявлен всего один ингредиент (не Феликс)");
                    message = "Вам следует добавить больше ингредиентов, посмотрите хорошенько в ящиках и не забудьте о праве 'обчистить холодильник'.";
                } else message="Кажется, у вас недостаточно продуктов для создания полноценного блюда, но мы подобрали рецепты блюд, которые пойдойдут вам, если добавить немного ингредиентов:";
                log.info("Полное совпадение не найдено");
                model.addAttribute("message", message);
                log.info("Перенаправление на страницу с котиками");
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
        log.info("Запрос на поиск по '" + word +"'");
        try {
            List<RecipeEntity> receptList = receptDAO.searchByWord(word);
            if (receptList.size() == 1) {
                model.addAttribute("recept", receptDAO.show(receptList.get(0).getId()));
                log.info("По запросу найдено одно совпадение");
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
            log.info("Запрос некорректен");
            return "output/notFound";
        }
    }

    @GetMapping("/add")
    public String add(){
        log.info("Запрос на добавление рецепта" +
                "");
        return "/addForm";
    }
    @PostMapping("/add")
    public String save(@RequestParam String head, @RequestParam String ingredients, @RequestParam String preparation, @RequestParam String recipe){

        String[] products = ingredients.split(";");
        String[] arrPreparation = preparation.split(";");
        String[] cooking = recipe.split(";");
        RecipeModel recipeModel = new RecipeModel(head, products, arrPreparation, cooking);
        RecipeEntity recipeEntity = receptDAO.save(recipeModel);
        log.info("Добавлено: "+head);
        return "redirect:/allRecipes";
    }
}
