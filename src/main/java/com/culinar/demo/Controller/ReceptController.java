package com.culinar.demo.Controller;

import com.culinar.demo.ReceptDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
public class ReceptController {
    @Autowired
    private ReceptDAO receptDAO;


    @GetMapping()
        public String index (Model model) throws SQLException {
        model.addAttribute("recipes", receptDAO.index());
        return "output/recepts";
    }
    @GetMapping("/{id}")
    public String show (@PathVariable ("id") int id, Model model) {
        model.addAttribute("recept", receptDAO.show(id));
        return "output/recept";
    }
}
