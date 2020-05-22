package io.niufen.springboot.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LayoutController {

    @RequestMapping("/copyright")
    public String index() {
        return "copyright";
    }

    @RequestMapping("/fragment")
    public String fragment() {
        return "fragment";
    }

    @RequestMapping("/layout")
    public String layout() {
        return "layout";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }


}
