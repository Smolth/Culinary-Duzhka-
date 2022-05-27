package com.culinar.demo.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GreetingController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public String error(){
        return "404exception";
    }

    @GetMapping("/")
    public String greeting() {
            return "greeting";
    }
    @GetMapping("/recept")
    public String recept() {
        return "output/recept";
    }
}
